package model;

public interface InterestBearing {
    double interestRate();

    long getBalance();

    default double yearlyInterest() {
        double rate = interestRate();
        return rate == 0.0 ? 0.0 : getBalance() * rate / 100.0;
    }
}
