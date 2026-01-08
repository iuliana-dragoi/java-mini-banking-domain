package service;

import model.Account;
import model.Customer;

import java.util.List;
import java.util.Map;

public interface BankService {

    Account createSavingsAccount(Customer customer);

    Account createBusinessAccount(Customer customer);

    Account createCreditAccount(Customer customer);

    void deposit(long accountId, double amount);

    void withdraw(long accountId, double amount);

    void transfer(long fromId, long toId, double amount);

    void addAccount(Customer customer, Account account);

    List<Account> getAccounts(Customer customer);

    Map<Customer, List<Account>> getAllAccounts();
}
