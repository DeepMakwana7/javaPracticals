package model;

import exception.AccountNotFoundException;
import exception.BankException;
import exception.InsufficientFundsException;
import exception.InvalidAmountException;
import model.annotation.Id;
import model.annotation.MaxLength;
import model.annotation.Positive;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Account implements Transactable, InterestBearing, Serializable,
        Comparable<Account> {
    private static final long serialVersionUID = 1L;

    @Id
    private final String accountNumber;
    @MaxLength(50)
    private String ownerName;
    @Positive
    private long balance;
    private boolean active;
    private transient ReentrantLock accountLock = new ReentrantLock();

    private static long accountCounter;

    public Account(String ownerName, long openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = openingBalance;
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        accountLock = new ReentrantLock();
        reserveAccountNumber(accountNumber);
    }

    private static synchronized String generateAccountNumber() {
        return String.format("AC%04d", ++accountCounter);
    }

    private static synchronized void reserveAccountNumber(String accountNumber) {
        if (accountNumber.startsWith("AC")) {
            try {
                accountCounter = Math.max(accountCounter,
                        Long.parseLong(accountNumber.substring(2)));
            } catch (NumberFormatException e) {
                throw new IllegalStateException(
                        "Invalid serialized account number: " + accountNumber, e);
            }
        }
    }

    @Override
    public void deposit(long amount) throws InvalidAmountException {
        accountLock.lock();
        try {
            if (amount <= 0) {
                throw new InvalidAmountException("Deposit amount must be positive.");
            }
            balance += amount;
        } finally {
            accountLock.unlock();
        }
    }

    @Override
    public boolean withdraw(long amount)
            throws InsufficientFundsException, InvalidAmountException {
        accountLock.lock();
        try {
            if (amount <= 0) {
                throw new InvalidAmountException("Withdrawal amount must be positive.");
            }
            if (!canWithdraw(amount)) {
                throw new InsufficientFundsException(
                        "Insufficient funds to withdraw " + amount + ".",
                        withdrawalShortfall(amount));
            }
            balance -= amount;
            return true;
        } finally {
            accountLock.unlock();
        }
    }

    public void transfer(Account to, long amount) throws BankException {
        if (to == null) {
            throw new AccountNotFoundException("Destination account was not found.");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be positive.");
        }

        Account first = accountNumber.compareTo(to.accountNumber) <= 0 ? this : to;
        Account second = first == this ? to : this;
        first.accountLock.lock();
        second.accountLock.lock();
        try {
            transferLocked(to, amount);
        } finally {
            second.accountLock.unlock();
            first.accountLock.unlock();
        }
    }

    private void transferLocked(Account to, long amount) throws BankException {
        boolean debited = false;
        try {
            withdraw(amount);
            debited = true;
            to.deposit(amount);
        } catch (BankException exception) {
            if (debited) {
                try {
                    deposit(amount);
                } catch (InvalidAmountException rollbackException) {
                    exception.addSuppressed(rollbackException);
                }
            }
            throw exception;
        }
    }

    public abstract double interestRate();

    public abstract boolean canWithdraw(long amount);

    protected abstract long withdrawalShortfall(long amount);

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        accountLock.lock();
        try {
            return balance;
        } finally {
            accountLock.unlock();
        }
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Account{accountNumber='" + accountNumber + "', ownerName='" + ownerName
                + "', balance=" + balance + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public int compareTo(Account other) {
        return accountNumber.compareTo(other.accountNumber);
    }
}
