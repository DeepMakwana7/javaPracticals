package service;

import exception.BankException;
import model.Account;
import model.TransactionType;

public class AccountTransaction implements Runnable {
    private final Account account;
    private final TransactionType type;
    private final long amount;

    public AccountTransaction(Account account, TransactionType type, long amount) {
        if (account == null || type == null) {
            throw new IllegalArgumentException("Account and transaction type are required.");
        }
        if (type == TransactionType.TRANSFER) {
            throw new IllegalArgumentException("A transfer requires a destination account.");
        }
        this.account = account;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            if (type == TransactionType.DEPOSIT) {
                account.deposit(amount);
            } else {
                account.withdraw(amount);
            }
        } catch (BankException e) {
            System.out.println(Thread.currentThread().getName()
                    + " transaction rejected: " + e.getMessage());
        }
    }
}
