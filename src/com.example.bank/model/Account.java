package model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public sealed abstract class Account permits SavingsAccount, BusinessAccount, CreditAccount, InvestmentAccount, PremiumAccount {

    private final long id;
    protected double balance;
    private Customer owner;
    protected final List<Transaction> transactions = new CopyOnWriteArrayList<>();
    protected final Object lock = new Object();

    protected Account(long id, Customer owner) {
        this.id = id;
        this.owner = owner;
        this.balance = 0;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        synchronized (lock) {
            return balance;
        }
    }

    public Customer getOwner() {
        return owner;
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(this.transactions);
        // Prevents list modification from outside
    }

    public void deposit(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        synchronized (lock) {
            balance += amount;
            transactions.add(Transaction.deposit(amount));
        }
    }

    public abstract void withdraw(double amount);

    public abstract AccountType getType();

    protected void record(Transaction tx) {
        transactions.add(tx);
    }

    @Override
    public String toString() {
        return "Account{id=%d, balance=%.2f, owner=%s}".formatted(id, balance, owner.name());
    }
}
