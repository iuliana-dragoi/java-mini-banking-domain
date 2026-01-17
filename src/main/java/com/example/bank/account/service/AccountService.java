package com.example.bank.account.service;

import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;

import java.util.List;

public interface AccountService {

    void deposit(long accountId, double amount, String description);
    void withdraw(long accountId, double amount, String description);
    void transfer(long fromId, long toId, double amount, String description);
    Account openAccount(AccountType type, String ownerName, long customerId);
    List<Account> findByCustomerId(long customerId);
}
