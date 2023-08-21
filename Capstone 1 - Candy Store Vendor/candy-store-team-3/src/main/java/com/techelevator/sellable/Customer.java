package com.techelevator.sellable;

public class Customer {
    private double accountBalance;

    public Customer(double startingBalance) {
        this.accountBalance = startingBalance;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void deductFromBalance(double deduction) {
        accountBalance -= deduction;

    }
}
