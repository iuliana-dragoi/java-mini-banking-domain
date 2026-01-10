package account.exceptions;

import bank.exceptions.BankException;

public class InsufficientFundException extends BankException {
    
    public InsufficientFundException(String message) {
        super(message);
    }
}
