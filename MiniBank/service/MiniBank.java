package service;

import exception.BankException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import model.Account;
import model.BankInfo;
import model.Command;
import model.MenuOption;

import static java.lang.System.out;

public class MiniBank {
    private MiniBank() {
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BankInfo bankInfo = new BankInfo("MiniBank", "Main Branch");
        out.println("=== " + bankInfo.name() + " | " + bankInfo.branch() + " ===");

        try (BankService bankService = new BankService(
                    Path.of("data", "accounts.ser"),
                    Path.of("logs"),
                    Path.of("reports", "end-of-day.txt"));
                Scanner scanner = new Scanner(System.in)) {
            out.println("Loaded " + bankService.listAccounts().size() + " saved account(s).");
            runMenu(bankService, scanner);
        }
    }

    private static void runMenu(BankService bankService, Scanner scanner) {
        boolean running = true;
        while (running) {
            out.println();
            out.println("MiniBank Menu");
            MenuOption[] options = MenuOption.values();
            for (int i = 0; i < options.length; i++) {
                out.println((i + 1) + ". " + options[i].name().replace('_', ' '));
            }
            out.print("Choose an option: ");

            String input = readLine(scanner);
            if (input == null) {
                out.println("Saving accounts before exit.");
                saveBeforeExit(bankService);
                break;
            }

            int choice;
            try {
                choice = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                out.println("Enter a number from 1 to " + options.length + ".");
                continue;
            }

            if (choice < 1 || choice > options.length) {
                out.println("Choose a listed menu number.");
                continue;
            }

            try {
                switch (options[choice - 1]) {
                    case OPEN_ACCOUNT -> openAccount(bankService, scanner);
                    case DEPOSIT -> deposit(bankService, scanner);
                    case WITHDRAW -> withdraw(bankService, scanner);
                    case TRANSFER -> transfer(bankService, scanner);
                    case STATEMENT -> statement(bankService, scanner);
                    case LIST_ACCOUNTS -> listAccounts(bankService);
                    case PROCESS_BATCH -> processBatch(bankService, scanner);
                    case REPORT -> out.println(bankService.generateReport());
                    case EXIT -> {
                        bankService.saveAccounts();
                        out.println("Accounts saved. Goodbye!");
                        running = false;
                    }
                }
            } catch (BankException | IOException | IllegalArgumentException e) {
                out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private static void openAccount(BankService bankService, Scanner scanner)
            throws BankException, IOException {
        out.println("Account type: 1. Savings  2. Current  3. Fixed Deposit");
        String typeChoice = prompt(scanner, "Choose account type: ");
        String type = switch (typeChoice == null ? "" : typeChoice.trim()) {
            case "1" -> "SAVINGS";
            case "2" -> "CURRENT";
            case "3" -> "FIXED_DEPOSIT";
            default -> throw new IllegalArgumentException("Invalid account type.");
        };
        String name = prompt(scanner, "Customer name: ");
        String email = prompt(scanner, "Email: ");
        String mobile = prompt(scanner, "Mobile: ");
        long openingBalance = readLong(scanner, "Opening balance (rupees): ");
        long accountLimit = 0;
        if ("SAVINGS".equals(type)) {
            accountLimit = readLong(scanner, "Minimum balance (rupees): ");
        } else if ("CURRENT".equals(type)) {
            accountLimit = readLong(scanner, "Overdraft limit (rupees): ");
        }

        Account account = bankService.openAccount(
                type, name, email, mobile, openingBalance, accountLimit);
        out.println("Account opened: " + account);
    }

    private static void deposit(BankService bankService, Scanner scanner)
            throws BankException, IOException {
        String accountNumber = prompt(scanner, "Account number: ");
        long amount = readLong(scanner, "Deposit amount (rupees): ");
        bankService.deposit(accountNumber, amount);
        out.println("Deposit completed.");
    }

    private static void withdraw(BankService bankService, Scanner scanner)
            throws BankException, IOException {
        String accountNumber = prompt(scanner, "Account number: ");
        long amount = readLong(scanner, "Withdrawal amount (rupees): ");
        bankService.withdraw(accountNumber, amount);
        out.println("Withdrawal completed.");
    }

    private static void transfer(BankService bankService, Scanner scanner)
            throws BankException, IOException {
        String fromAccount = prompt(scanner, "From account number: ");
        String toAccount = prompt(scanner, "To account number: ");
        long amount = readLong(scanner, "Transfer amount (rupees): ");
        bankService.transfer(fromAccount, toAccount, amount);
        out.println("Transfer completed.");
    }

    private static void statement(BankService bankService, Scanner scanner)
            throws BankException {
        String accountNumber = prompt(scanner, "Account number: ");
        out.println(bankService.statement(accountNumber));
    }

    private static void listAccounts(BankService bankService) {
        ArrayList<Account> accounts = bankService.listAccounts();
        if (accounts.isEmpty()) {
            out.println("No accounts are currently open.");
            return;
        }
        for (Account account : accounts) {
            out.println(account);
        }
    }

    private static void processBatch(BankService bankService, Scanner scanner)
            throws BankException, IOException {
        out.println("Enter one DEPOSIT or WITHDRAW command per line (TYPE ACCOUNT AMOUNT).");
        long requestedCount = readLong(scanner, "Number of transactions: ");
        if (requestedCount < 0 || requestedCount > 1000) {
            throw new IllegalArgumentException("Batch size must be from 0 to 1000.");
        }
        int count = (int) requestedCount;

        ArrayList<Command> commands = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String line = prompt(scanner, "Transaction " + (i + 1) + ": ");
            commands.add(CommandParser.parse(line));
        }
        bankService.processBatch(commands);
        out.println("Processed " + commands.size() + " transaction(s).");
    }

    private static String prompt(Scanner scanner, String label) {
        out.print(label);
        String line = readLine(scanner);
        if (line == null) {
            throw new IllegalArgumentException("Input ended before the operation was complete.");
        }
        return line.trim();
    }

    private static long readLong(Scanner scanner, String label) {
        String input = prompt(scanner, label);
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Enter a whole number.", e);
        }
    }

    private static String readLine(Scanner scanner) {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private static void saveBeforeExit(BankService bankService) {
        try {
            bankService.saveAccounts();
        } catch (IOException e) {
            out.println("Could not save accounts: " + e.getMessage());
        }
    }
}
