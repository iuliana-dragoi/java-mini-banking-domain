package com.example.bank.bank.service;

import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;
import com.example.bank.account.service.AccountService;
import com.example.bank.customer.model.Customer;
import com.example.bank.customer.service.CustomerService;

public class BankService { // orchestrator

    private final AccountService accountService;
    private final CustomerService customerService;

    public BankService(AccountService accountService, CustomerService customerService) {
        this.accountService = accountService;
        this.customerService = customerService;
    }

    public Account openAccountForCustomer(long customerId, String ownerName, AccountType type) {
        Customer customer = customerService.findById(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found: " + customerId);
        }

        return accountService.openAccount(type, ownerName, customerId);
    }

    public Account createCustomerWithAccount(String ownerName, String email, AccountType type) {
        Customer customer = customerService.create(ownerName, email);
        return accountService.openAccount(type, customer.name(), customer.id());
    }
}
