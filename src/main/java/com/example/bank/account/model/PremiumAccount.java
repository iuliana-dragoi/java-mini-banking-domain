package com.example.bank.account.model;

import com.example.bank.bank.exceptions.BankException;

public final class PremiumAccount extends Account {

    private final double overdraftLimit;
    private final double bonusInterestRate;

    public PremiumAccount(long id, String iban, String ownerName, long customerId) {
        super(id, iban, ownerName, customerId);
        this.overdraftLimit = 10000;
        this.bonusInterestRate = 0.02;
    }

    @Override
    public void withdraw(double amount) throws BankException {
        synchronized (lock) {
            if (amount > balance + overdraftLimit) {
                throw new BankException("Overdraft limit exceeded for PremiumAccount");
            }

            balance -= amount;
        }
    }

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        applyBonusInterest(amount);
    }

    private void applyBonusInterest(double amount) {
        double bonus = amount * bonusInterestRate;
        balance += bonus;
    }

    @Override
    public AccountType getType() {
        return AccountType.PREMIUM;
    }
}
