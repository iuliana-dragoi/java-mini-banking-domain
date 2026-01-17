package com.example.bank.account.exceptions;

import com.example.bank.bank.exceptions.BankException;

public class InsufficientFundException extends BankException {
    
    public InsufficientFundException(String message) {
        super(message);
    }
}
