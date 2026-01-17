package com.example.bank.transaction.repository;

import com.example.bank.transaction.model.Transaction;
import java.util.List;

public interface TransactionRepository {

    Transaction save(Transaction transaction);
    Transaction findById(Long id);
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findAll();
}
