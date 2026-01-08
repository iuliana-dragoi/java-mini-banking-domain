package model;

import exception.BankException;

public final class CreditAccount extends Account {

    private final double creditLimit;

    public CreditAccount(long id, Customer owner, double creditLimit) {
        super(id, owner);
        this.creditLimit = creditLimit;
    }

    @Override
    public void withdraw(double amount) {
        if(amount > balance + creditLimit) {
            throw new BankException("Credit limit exceded!");
        }

        balance -= amount;
        transactions.add(Transaction.withdraw(amount));
    }
}
