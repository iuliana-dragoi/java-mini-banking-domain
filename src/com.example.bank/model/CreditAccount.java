package model;

import exception.BankException;

public final class CreditAccount extends Account {

    private final double creditLimit;
    private final Object lock = new Object();

    public CreditAccount(long id, Customer owner, double creditLimit) {
        super(id, owner);
        this.creditLimit = creditLimit;
    }

    @Override
    public void withdraw(double amount) {
        synchronized (lock) {
            if(amount > balance + creditLimit) {
                throw new BankException("Credit limit exceded!");
            }

            balance -= amount;
            record(Transaction.withdraw(amount));
        }
    }

    @Override
    public AccountType getType() {
        return AccountType.CREDIT;
    }
}
