package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    private final long id;
    protected double balance;
    private Customer owner;

    private final List<Transaction> transactions = new ArrayList<>();

    protected Account(long id, Customer owner) {
        this.id = id;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public List<Transaction> getTransactions() {
        return List.copyOf(this.transactions);
        // Prevents list modification from outside
    }

    public void deposit(double amount) {
        assert amount > 0 : "Amount must be positive";
        balance += amount;
        transactions.add(Transaction.deposit(amount));
    }

    public abstract void withdraw(double amount);

    protected void recordTransaction(Transaction tx) {
        transactions.add(tx);
    }

    @Override
    public String toString() {
        return "Account{id=%d, balance=%.2f}".formatted(id, balance);
    }
}
