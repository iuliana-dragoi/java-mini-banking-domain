package account.model;

import bank.exceptions.BankException;

public final class BusinessAccount extends Account {

    private final double overdraftLimit;

    public BusinessAccount(long id, String iban, String ownerName, long customerId, double overdraftLimit) {
        super(id, iban, ownerName, customerId);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        synchronized (lock) {
            if(amount > balance + overdraftLimit) {
                throw new BankException("Overdraft limit exceeded!");
            }

            balance -= amount;
        }
    }

    @Override
    public AccountType getType() {
        return AccountType.BUSINESS;
    }
}
