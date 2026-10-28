package service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import model.Account;

public class TransactionProcessor {
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    private final ConcurrentHashMap<String, Account> accounts = new ConcurrentHashMap<>();

    public void submit(Runnable task) {
        executor.execute(task);
    }

    public void addAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        Account existing = accounts.putIfAbsent(account.getAccountNumber(), account);
        if (existing != null && existing != account) {
            throw new IllegalArgumentException(
                    "Account number already registered: " + account.getAccountNumber());
        }
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public Map<String, Account> getAccounts() {
        return Map.copyOf(accounts);
    }

    public void stop() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                    throw new IllegalStateException("Transaction processor did not terminate.");
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while stopping transaction processor.", e);
        }
    }
}
