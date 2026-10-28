package service;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import model.Account;
import model.Command;
import model.CurrentAccount;
import model.FixedDepositAccount;
import model.SavingsAccount;
import model.TransactionType;
import util.ReportGenerator;
import util.Repository;
import util.StatePersister;
import util.StatementFormatter;
import util.TransactionLog;
import util.Validator;

public class BankService implements AutoCloseable {
    private final Repository<Account> accounts = new Repository<>();
    private final TransactionProcessor processor = new TransactionProcessor();
    private final Path stateFile;
    private final Path logsFolder;
    private final Path reportFile;

    public BankService(Path stateFile, Path logsFolder, Path reportFile)
            throws IOException, ClassNotFoundException {
        if (stateFile == null || logsFolder == null || reportFile == null) {
            throw new IllegalArgumentException("State, log, and report paths are required.");
        }
        this.stateFile = stateFile;
        this.logsFolder = logsFolder;
        this.reportFile = reportFile;

        if (Files.exists(stateFile)) {
            for (Account account : StatePersister.load(stateFile)) {
                register(account);
            }
        }
    }

    public Account openAccount(String accountType, String ownerName, String email,
            String mobile, long openingBalance, long accountLimit)
            throws BankException, IOException {
        if (ownerName == null || ownerName.isBlank() || ownerName.length() > 50) {
            throw new IllegalArgumentException("Name must contain 1 to 50 characters.");
        }
        if (!Validator.isValidEmail(email)) {
            throw new IllegalArgumentException("Email address is invalid.");
        }
        if (!Validator.isValidMobile(mobile)) {
            throw new IllegalArgumentException("Mobile number is invalid.");
        }
        if (openingBalance < 0) {
            throw new InvalidAmountException("Opening balance cannot be negative.");
        }

        Account account = switch (accountType == null ? "" : accountType.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(ownerName, openingBalance, accountLimit);
            case "CURRENT" -> new CurrentAccount(ownerName, openingBalance, accountLimit);
            case "FIXED_DEPOSIT" -> new FixedDepositAccount(ownerName, openingBalance);
            default -> throw new IllegalArgumentException(
                    "Account type must be SAVINGS, CURRENT, or FIXED_DEPOSIT.");
        };
        register(account);
        saveAccounts();
        return account;
    }

    public void deposit(String accountNumber, long amount) throws BankException, IOException {
        Account account = findAccount(accountNumber);
        account.deposit(amount);
        TransactionLog.append(transactionLogPath(),
                "DEPOSIT " + accountNumber + " " + amount);
    }

    public void withdraw(String accountNumber, long amount) throws BankException, IOException {
        Account account = findAccount(accountNumber);
        account.withdraw(amount);
        TransactionLog.append(transactionLogPath(),
                "WITHDRAW " + accountNumber + " " + amount);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, long amount)
            throws BankException, IOException {
        Account from = findAccount(fromAccountNumber);
        Account to = findAccount(toAccountNumber);
        from.transfer(to, amount);
        TransactionLog.append(transactionLogPath(),
                "WITHDRAW " + fromAccountNumber + " " + amount);
        TransactionLog.append(transactionLogPath(),
                "DEPOSIT " + toAccountNumber + " " + amount);
    }

    public String statement(String accountNumber) throws AccountNotFoundException {
        return StatementFormatter.buildStatement(findAccount(accountNumber));
    }

    public ArrayList<Account> listAccounts() {
        ArrayList<Account> result = accounts.findAll();
        result.sort(Comparator.comparingLong(Account::getBalance)
                .thenComparing(Comparator.naturalOrder()));
        return result;
    }

    public void processBatch(List<Command> commands) throws BankException, IOException {
        if (commands == null) {
            throw new IllegalArgumentException("Batch cannot be null.");
        }
        CountDownLatch completed = new CountDownLatch(commands.size());
        AtomicReference<BankException> bankFailure = new AtomicReference<>();
        AtomicReference<IOException> ioFailure = new AtomicReference<>();

        for (Command command : commands) {
            if (command == null || command.type() == TransactionType.TRANSFER) {
                throw new IllegalArgumentException(
                        "Batch commands must be deposits or withdrawals; use the transfer option.");
            }
            findAccount(command.accountNumber());
        }

        for (Command command : commands) {
            processor.submit(() -> {
                try {
                    if (command.type() == TransactionType.DEPOSIT) {
                        deposit(command.accountNumber(), command.amount());
                    } else {
                        withdraw(command.accountNumber(), command.amount());
                    }
                } catch (BankException e) {
                    bankFailure.compareAndSet(null, e);
                } catch (IOException e) {
                    ioFailure.compareAndSet(null, e);
                } finally {
                    completed.countDown();
                }
            });
        }

        try {
            completed.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new BankException("Interrupted while processing transaction batch.", e);
        }
        if (bankFailure.get() != null) {
            throw bankFailure.get();
        }
        if (ioFailure.get() != null) {
            throw ioFailure.get();
        }
    }

    public String generateReport() throws IOException {
        return ReportGenerator.generate(logsFolder, reportFile);
    }

    public void saveAccounts() throws IOException {
        ArrayList<Account> currentAccounts = accounts.findAll();
        StatePersister.save(currentAccounts.toArray(new Account[0]), stateFile);
    }

    @Override
    public void close() {
        processor.stop();
    }

    private Account findAccount(String accountNumber) throws AccountNotFoundException {
        Account account = processor.getAccount(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account not found: " + accountNumber);
        }
        return account;
    }

    private void register(Account account) {
        processor.addAccount(account);
        accounts.save(account);
    }

    private Path transactionLogPath() {
        return logsFolder.resolve("transactions.log");
    }
}
