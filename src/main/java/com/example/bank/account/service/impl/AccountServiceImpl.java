package com.example.bank.account.service.impl;

import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;
import com.example.bank.account.repository.AccountRepository;
import com.example.bank.account.service.AccountFactory;
import com.example.bank.account.service.AccountService;
import com.example.bank.transaction.model.Transaction;
import com.example.bank.transaction.repository.TransactionRepository;
import com.example.bank.transaction.service.TransferService;

import java.util.List;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory factory;
    private final TransactionRepository transactionRepository;
    private final TransferService transferService;

    public AccountServiceImpl(AccountRepository accountRepository, AccountFactory factory, TransactionRepository transactionRepository, TransferService transferService) {
        this.accountRepository = accountRepository;
        this.factory = factory;
        this.transactionRepository = transactionRepository;
        this.transferService = transferService;
    }

    @Override
    public void deposit(long accountId, double amount, String description) {
        Account account = accountRepository.findById(accountId);
        account.deposit(amount);
        accountRepository.save(account);

        Transaction tx = Transaction.forDeposit(accountId, amount, description);
        transactionRepository.save(tx);
    }

    @Override
    public void withdraw(long accountId, double amount, String description) {
        Account account = accountRepository.findById(accountId);
        account.withdraw(amount);
        accountRepository.save(account);

        Transaction tx = Transaction.forWithdraw(accountId, amount, description);
        transactionRepository.save(tx);
    }

    @Override
    public void transfer(long fromId, long toId, double amount, String description) {
        transferService.transfer(fromId, toId, amount, description);
    }

    @Override
    public Account openAccount(AccountType type, String ownerName, long customerId) {
        Account account = factory.create(type, ownerName, customerId);
        accountRepository.save(account);
        return account;
    }

    @Override
    public List<Account> findByCustomerId(long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }
}
