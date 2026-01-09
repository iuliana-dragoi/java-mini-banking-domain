package service;

import exception.AccountNotFoundException;
import model.*;
import util.IdGenerator;

import java.util.*;

public class InMemoryBankService implements BankService {

    private final Map<Long, Account> accounts = new HashMap<>();
    private final Map<Customer, List<Account>> customerAccounts = new HashMap<>();

    @Override
    public Account createSavingsAccount(Customer customer) {
        long id = IdGenerator.nextId();
        Account account = new SavingsAccount(id, customer);
        accounts.put(id, account);
        addAccount(customer, account);
        return account;
    }

    @Override
    public Account createBusinessAccount(Customer customer) {
        long id = IdGenerator.nextId();
        Account account = new BusinessAccount(id, customer, 10000);
        accounts.put(id, account);
        addAccount(customer, account);
        return account;
    }

    @Override
    public Account createCreditAccount(Customer customer) {
        long id = IdGenerator.nextId();
        Account account = new CreditAccount(id, customer, 5000);
        accounts.put(id, account);
        addAccount(customer, account);
        return account;
    }

    @Override
    public Account createPremiumAccount(Customer customer) {
        long id = IdGenerator.nextId();
        Account account = new PremiumAccount(id, customer);
        accounts.put(id, account);
        addAccount(customer, account);
        return account;
    }

    @Override
    public Account createInvestmentAccount(Customer customer) {
        long id = IdGenerator.nextId();
        Account account = new InvestmentAccount(id, customer);
        accounts.put(id, account);
        addAccount(customer, account);
        return account;
    }

    @Override
    public void deposit(long accountId, double amount) {
        findAccount(accountId).deposit(amount);
    }

    @Override
    public void withdraw(long accountId, double amount) {
        findAccount(accountId).withdraw(amount);
    }

    @Override
    public void transfer(long fromId, long toId, double amount) {
        var from = findAccount(fromId);
        var to = findAccount(toId);

        from.withdraw(amount);
        to.deposit(amount);
    }

    @Override
    public void addAccount(Customer customer, Account account) {
        customerAccounts.computeIfAbsent(customer, c -> new ArrayList<>()).add(account);
    }

    @Override
    public List<Account> getAccounts(Customer customer) {
        return customerAccounts.containsKey(customer) ?
                List.copyOf(customerAccounts.get(customer)) : Collections.emptyList();
    }

    @Override
    public Map<Customer, List<Account>> getAllAccounts() {
        Map<Customer, List<Account>> snapshot = new HashMap<>();
        customerAccounts.forEach((c, accounts) -> snapshot.put(c, List.copyOf(accounts)));
        return snapshot;
    }

    private Account findAccount(long id) {
        Account account = accounts.get(id);
        if(account == null) {
            throw new AccountNotFoundException(id);
        }

        return account;
    }
}
