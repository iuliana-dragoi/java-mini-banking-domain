package com.example.bank.account.repository;

import com.example.bank.account.model.Account;
import java.util.*;

public class AccountRepositoryImpl implements AccountRepository {

    private Map<Long, Account> accounts = new HashMap<>();
    private Map<Long, List<Long>> customerAccountIds = new HashMap<>();

    @Override
    public Account save(Account account) {
        accounts.put(account.getId(), account);
        customerAccountIds.computeIfAbsent(account.getCustomerId(), k -> new ArrayList<>());
        if (!customerAccountIds.get(account.getCustomerId()).contains(account.getId())) {
            customerAccountIds.get(account.getCustomerId()).add(account.getId());
        }

        return account;
    }

    @Override
    public Account findById(Long id) {
        return accounts.get(id);
    }

    @Override
    public Account findByIban(String iban) {
        return accounts.values().stream()
                .filter(a -> a.getIban().equals(iban))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    @Override
    public void delete(Long id) {
        Account removed = accounts.remove(id);
        if (removed != null) {
            List<Long> ids = customerAccountIds.get(removed.getCustomerId());
            if (ids != null) {
                ids.remove(id);
                if (ids.isEmpty()) {
                    customerAccountIds.remove(removed.getCustomerId());
                }
            }
        }
    }

    @Override
    public List<Account> findByCustomerId(Long customerId) {
        List<Long> ids = customerAccountIds.get(customerId);
        if (ids == null) return List.of();
        return ids.stream()
                .map(accounts::get)
                .toList();
    }
}
