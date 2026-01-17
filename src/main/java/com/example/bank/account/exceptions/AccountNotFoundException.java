package com.example.bank.account.exceptions;

import com.example.bank.bank.exceptions.BankException;

public class AccountNotFoundException extends BankException {

    public AccountNotFoundException(long id) {
        super("Account not found: " + id);
    }
}
