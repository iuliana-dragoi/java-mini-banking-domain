package model;

import java.util.ArrayList;
import java.util.List;

public sealed abstract class Account permits SavingsAccount, BusinessAccount, CreditAccount, InvestmentAccount, PremiumAccount {

    private final long id;
    protected double balance;
    private Customer owner;
    protected final List<Transaction> transactions = new ArrayList<>();

    protected Account(long id, Customer owner) {
        this.id = id;
        this.owner = owner;
        this.balance = 0;
    }

    public long getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getOwner() {
        return owner;
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

    protected void record(Transaction tx) {
        transactions.add(tx);
    }

    @Override
    public String toString() {
        return "Account{id=%d, balance=%.2f}".formatted(id, balance);
    }

    public static class Statistics {

        public static double totalDeposits(Account account) {
            return account.getTransactions().stream()
                    .filter(t -> t.type().equals("DEPOSIT"))
                    .mapToDouble(Transaction::amount)
                    .sum();
        }

        public static double totalWithdrawals(Account account) {
            return account.getTransactions().stream()
                    .filter(t -> t.type().equals("WITHDRAW"))
                    .mapToDouble(Transaction::amount)
                    .sum();
        }
    }
}
