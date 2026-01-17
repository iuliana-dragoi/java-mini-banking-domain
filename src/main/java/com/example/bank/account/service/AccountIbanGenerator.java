package com.example.bank.account.service;

import com.example.bank.account.model.AccountType;
import com.example.bank.account.model.Iban;

public interface AccountIbanGenerator {

    Iban generate(AccountType type);
}
