package model;

import exception.InsufficientFundException;

public final class SavingsAccount extends Account {

    private final Object lock = new Object();

    public SavingsAccount(long id, Customer owner) {
        super(id, owner);
    }

    @Override
    public void withdraw(double amount) {
       if(amount <= 0) {
           throw new IllegalArgumentException("Amounte must be positive!");
       }

        synchronized(lock) {
            if(amount > this.balance) {
                throw new InsufficientFundException("Not enough money. Balance = " + balance);
            }
            balance -= amount;
            record(Transaction.withdraw(amount));
        }
    }

    @Override
    public AccountType getType() {
        return AccountType.SAVINGS;
    }
}
