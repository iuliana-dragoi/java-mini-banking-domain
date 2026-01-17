package com.example.bank.account.service;

import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;

public interface AccountFactory {

    Account create(AccountType type, String ownerName, long customerId);
}
