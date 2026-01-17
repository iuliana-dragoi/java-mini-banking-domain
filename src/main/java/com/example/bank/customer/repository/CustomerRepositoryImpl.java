package com.example.bank.customer.repository;

import com.example.bank.account.model.Account;
import com.example.bank.customer.model.Customer;
import java.util.*;

public class CustomerRepositoryImpl implements CustomerRepository {

    private Map<Long, Customer> database = new HashMap<>();
    private Map<Long, List<Account>> customerAccounts = new HashMap<>();

    @Override
    public Customer save(Customer customer) {
        database.put(customer.id(), customer);
        return database.get(customer.id());
    }

    @Override
    public Customer findById(Long id) {
        return database.get(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> snapShot = new ArrayList<>();
        database.forEach((id, customer) -> snapShot.add(customer));
        return snapShot;
    }

    @Override
    public void addAccountToCustomer(Long customerId, Account account) {
        customerAccounts.computeIfAbsent(customerId, c -> new ArrayList<>()).add(account);
    }

    @Override
    public List<Account> getCustomerAccounts(Long customerId) {
        return customerAccounts.containsKey(customerId) ?
                List.copyOf(customerAccounts.get(customerId)) : Collections.emptyList();
    }
}
