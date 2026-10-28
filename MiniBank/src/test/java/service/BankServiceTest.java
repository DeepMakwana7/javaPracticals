package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import exception.InsufficientFundsException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import model.Account;
import model.Command;
import model.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BankServiceTest {
    @TempDir
    Path tempDirectory;

    private BankService bankService;

    @BeforeEach
    void setUp() throws Exception {
        bankService = new BankService(
                tempDirectory.resolve("accounts.ser"),
                tempDirectory.resolve("logs"),
                tempDirectory.resolve("reports").resolve("end-of-day.txt"));
    }

    @AfterEach
    void tearDown() {
        bankService.close();
    }

    @Test
    void depositIncreasesBalance() throws Exception {
        Account account = bankService.openAccount(
                "SAVINGS", "Ava", "ava@example.com", "9876543210", 100, 50);

        bankService.deposit(account.getAccountNumber(), 75);

        assertEquals(175, account.getBalance());
    }

    @Test
    void withdrawalOverAvailableBalanceThrowsInsufficientFunds() throws Exception {
        Account account = bankService.openAccount(
                "SAVINGS", "Noah", "noah@example.com", "9876543211", 100, 50);

        assertThrows(InsufficientFundsException.class,
                () -> bankService.withdraw(account.getAccountNumber(), 51));
        assertEquals(100, account.getBalance());
    }

    @Test
    void concurrentBatchDepositsKeepBalanceCorrect() throws Exception {
        Account account = bankService.openAccount(
                "SAVINGS", "Mia", "mia@example.com", "9876543212", 0, 0);
        List<Command> commands = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            commands.add(new Command(
                    TransactionType.DEPOSIT, account.getAccountNumber(), 1));
        }

        bankService.processBatch(commands);

        assertEquals(1_000, account.getBalance());
    }
}
