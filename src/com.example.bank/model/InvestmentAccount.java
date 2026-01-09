package model;

import exception.BankException;

public final class InvestmentAccount extends Account {

    private final double interestRate = 0.05;

    public InvestmentAccount(long id, Customer owner) {
        super(id, owner);
    }

    @Override
    public void withdraw(double amount) {
        synchronized (lock) {
            if(amount > balance) {
                throw new BankException("Insufficient funds in Investment Account!");
            }

            balance -= amount;
            record(Transaction.withdraw(amount));
        }
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        transactions.add(Transaction.applyInterest(interest));
    }

    public double getProjectedBalance(int years) {
        return balance * Math.pow(1 + interestRate, years);
    }

    @Override
    public AccountType getType() {
        return AccountType.INVESTMENT;
    }
}
