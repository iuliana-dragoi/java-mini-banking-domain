package com.example.bank.transaction.service;

import com.example.bank.account.model.Account;
import com.example.bank.account.repository.AccountRepository;
import com.example.bank.transaction.model.Transaction;
import com.example.bank.transaction.repository.TransactionRepository;

public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction transfer(Long fromAccountId, Long toAccountId, double amount, String description) {

        if(fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer to the same account!");
        }

        if(amount < 0) {
            throw new IllegalArgumentException("Transfer amount must be positive!");
        }

        Account fromAccount = accountRepository.findById(fromAccountId);
        Account toAccount = accountRepository.findById(toAccountId);

        if(fromAccount == null) {
            throw new IllegalArgumentException("Source account not found!");
        }

        if(toAccount == null) {
            throw new IllegalArgumentException("Target account not found!");
        }

        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            Transaction tx = Transaction.forTransfer(fromAccountId, toAccountId, amount, description);
            return transactionRepository.save(tx);
        }
        catch (Exception e) {
            throw new RuntimeException("Transfer failed " + e.getMessage(), e);
        }
    }
}
