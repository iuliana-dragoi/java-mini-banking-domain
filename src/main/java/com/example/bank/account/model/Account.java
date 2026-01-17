package com.example.bank.account.model;

public sealed abstract class Account permits SavingsAccount, BusinessAccount, CreditAccount, InvestmentAccount, PremiumAccount {

    private final long id;
    private String iban;
    private long customerId;
    private String ownerName;
    protected double balance;
    protected final Object lock = new Object();

    public Account(long id, String iban, String ownerName, long customerId) {
        this.id = id;
        this.iban = iban;
        this.ownerName = ownerName;
        this.customerId = customerId;
        this.balance = 0.0;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        synchronized (lock) {
            return balance;
        }
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getIban() {
        return iban;
    }

    public Object getLock() {
        return lock;
    }

    public void deposit(double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        synchronized (lock) {
            balance += amount;
            System.out.println("Deposit: " + Thread.currentThread().getName() + ", balance=" + this.getBalance());
        }
    }

    public abstract void withdraw(double amount);

    public abstract AccountType getType();

    @Override
    public String toString() {
        return "Account{id=%d, balance=%.2f}".formatted(id, balance);
    }
}
