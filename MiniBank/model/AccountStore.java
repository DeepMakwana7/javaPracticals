package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountStore {
    private final HashMap<String, Account> accounts = new HashMap<>();
    private final ArrayList<String> transactionHistory = new ArrayList<>();

    public synchronized void add(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        String accountNumber = account.getAccountNumber();
        Account existing = accounts.putIfAbsent(accountNumber, account);
        if (existing != null && existing != account) {
            throw new IllegalArgumentException("Account number already exists: " + accountNumber);
        }
    }

    public synchronized Account get(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public synchronized ArrayList<Account> getAll() {
        return new ArrayList<>(accounts.values());
    }

    public synchronized void addTransaction(String transaction) {
        if (transaction == null || transaction.isBlank()) {
            throw new IllegalArgumentException("Transaction history entry cannot be blank.");
        }
        transactionHistory.add(transaction);
    }

    public synchronized ArrayList<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    public synchronized ArrayList<Account> sortedByBalance() {
        ArrayList<Account> sortedAccounts = new ArrayList<>(accounts.values());
        sortedAccounts.sort(Comparator.comparingLong(Account::getBalance)
                .thenComparing(Comparator.naturalOrder()));
        return sortedAccounts;
    }
}
