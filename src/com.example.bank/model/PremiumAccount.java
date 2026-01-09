package model;

import exception.BankException;

public final class PremiumAccount extends Account {

    private final double overdraftLimit;
    private final double bonusInterestRate;

    public PremiumAccount(long id, Customer owner) {
        super(id, owner);
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
            record(Transaction.withdraw(amount));
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
        transactions.add(Transaction.applyBonusInterest(bonus));
    }

    @Override
    public AccountType getType() {
        return AccountType.PREMIUM;
    }
}
