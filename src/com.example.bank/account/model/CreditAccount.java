package account.model;

import bank.exceptions.BankException;

public final class CreditAccount extends Account {

    private final double creditLimit;

    public CreditAccount(long id, String iban, String ownerName, long customerId, double creditLimit) {
        super(id, iban, ownerName, customerId);
        this.creditLimit = creditLimit;
    }

    @Override
    public void withdraw(double amount) {
        synchronized (lock) {
            if(amount > balance + creditLimit) {
                throw new BankException("Credit limit exceded!");
            }

            balance -= amount;
        }
    }

    @Override
    public AccountType getType() {
        return AccountType.CREDIT;
    }
}
