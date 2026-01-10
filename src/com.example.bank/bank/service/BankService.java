package bank.service;

import account.model.Account;
import account.model.AccountType;
import account.service.AccountService;
import customer.model.Customer;
import customer.service.CustomerService;

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
