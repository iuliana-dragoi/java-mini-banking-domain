package com.example.bank.account.model;

import com.example.bank.account.exceptions.InsufficientFundException;

public final class SavingsAccount extends Account {

    public SavingsAccount(long id, String iban, String ownerName, long customerId) {
        super(id, iban, ownerName, customerId);
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
            System.out.println("Withdraw: " + Thread.currentThread().getName() + ", balance=" + this.getBalance());
        }
    }

    @Override
    public AccountType getType() {
        return AccountType.SAVINGS;
    }
}
