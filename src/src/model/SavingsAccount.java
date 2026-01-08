package model;

import exception.InsufficientFundException;

public class SavingsAccount extends Account {

    public SavingsAccount(long id, Customer owner) {
        super(id, owner);
    }

    @Override
    public void withdraw(double amount) {
        assert amount > 0 : "Amount must be positive";

        if(amount > this.balance) {
            throw new InsufficientFundException("Not enough money. Balance = " + balance);
        }

        balance -= amount;
        recordTransaction(Transaction.withdraw(amount));
    }
}
