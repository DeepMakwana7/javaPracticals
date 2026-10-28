package model;

import java.io.Serializable;

public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;

    private final long overdraftLimit;

    public CurrentAccount(String ownerName, long openingBalance, long overdraftLimit) {
        super(ownerName, openingBalance);
        if (overdraftLimit < 0 || openingBalance < -overdraftLimit) {
            throw new IllegalArgumentException(
                    "Overdraft limit must be non-negative and opening balance cannot exceed it.");
        }
        this.overdraftLimit = overdraftLimit;
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        if (amount <= 0) {
            return false;
        }

        long balance = getBalance();
        if (balance < -overdraftLimit) {
            return false;
        }
        if (amount <= balance) {
            return true;
        }
        if (balance >= 0) {
            return amount - balance <= overdraftLimit;
        }
        return amount <= overdraftLimit + balance;
    }

    @Override
    protected long withdrawalShortfall(long amount) {
        long balance = getBalance();
        long available;
        if (balance >= 0 && balance > Long.MAX_VALUE - overdraftLimit) {
            available = Long.MAX_VALUE;
        } else {
            available = overdraftLimit + balance;
        }
        return Math.max(1, amount - available);
    }
}
