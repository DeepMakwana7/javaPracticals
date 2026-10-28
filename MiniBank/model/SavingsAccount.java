package model;

import java.io.Serializable;

public class SavingsAccount extends Account {
    private static final long serialVersionUID = 1L;

    private final long minBalance;

    public SavingsAccount(String ownerName, long openingBalance, long minBalance) {
        super(ownerName, openingBalance);
        if (minBalance < 0 || openingBalance < minBalance) {
            throw new IllegalArgumentException(
                    "Opening balance must be at least the non-negative minimum balance.");
        }
        this.minBalance = minBalance;
    }

    public long getMinBalance() {
        return minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        long balance = getBalance();
        return amount > 0 && balance >= minBalance && amount <= balance - minBalance;
    }

    @Override
    protected long withdrawalShortfall(long amount) {
        long available = Math.max(0, getBalance() - minBalance);
        return amount - available;
    }
}
