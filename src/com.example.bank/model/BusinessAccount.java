package model;

import exception.BankException;

public final class BusinessAccount extends Account {

    private final double overdraftLimit;

    public BusinessAccount(long id, Customer owner, double overdraftLimit) {
        super(id, owner);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        synchronized (lock) {
            if(amount > balance + overdraftLimit) {
                throw new BankException("Overdraft limit exceeded!");
            }

            balance -= amount;
            record(Transaction.withdraw(amount));
        }
    }

    @Override
    public AccountType getType() {
        return AccountType.BUSINESS;
    }
}
