package com.example.bank.account.repository;

import com.example.bank.account.model.Account;

import java.util.List;

public interface AccountRepository {

    Account save(Account account);
    Account findById(Long id);
    Account findByIban(String iban);
    List<Account> findAll();
    void delete(Long id);
    List<Account> findByCustomerId(Long customerId);
}
