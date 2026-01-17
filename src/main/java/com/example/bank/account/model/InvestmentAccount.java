package com.example.bank.account.model;

import com.example.bank.bank.exceptions.BankException;

public final class InvestmentAccount extends Account {

    private final double interestRate = 0.05;

    public InvestmentAccount(long id, String iban, String ownerName, long customerId) {
        super(id, iban, ownerName, customerId);
    }

    @Override
    public void withdraw(double amount) {
        synchronized (lock) {
            if(amount > balance) {
                throw new BankException("Insufficient funds in Investment Account!");
            }

            balance -= amount;
        }
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
    }

    public double getProjectedBalance(int years) {
        return balance * Math.pow(1 + interestRate, years);
    }

    @Override
    public AccountType getType() {
        return AccountType.INVESTMENT;
    }
}
