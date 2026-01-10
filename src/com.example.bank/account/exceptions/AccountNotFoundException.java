package account.exceptions;

import bank.exceptions.BankException;

public class AccountNotFoundException extends BankException {

    public AccountNotFoundException(long id) {
        super("Account not found: " + id);
    }
}
