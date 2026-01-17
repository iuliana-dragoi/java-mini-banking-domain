package com.example.bank.transaction.repository;

import com.example.bank.transaction.model.Transaction;

import java.util.*;

public class TransactionRepositoryImpl implements TransactionRepository{

    private final Map<Long, Transaction> database = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Transaction save(Transaction transaction) {
        Transaction tx = new Transaction(
            currentId++,
            transaction.fromAccountId(),
            transaction.toAccountId(),
            transaction.amount(),
            transaction.type(),
            transaction.instant(),
            transaction.description()
        );

        database.put(tx.id(), tx);
        return tx;
    }

    @Override
    public Transaction findById(Long id) {
        return database.get(id);
    }

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        return database.values().stream()
                .filter(tx -> tx.involvesAccount(accountId))
                .sorted(Comparator.comparing(Transaction::instant).reversed()) // newest first
                .toList();
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(database.values());
    }
}
