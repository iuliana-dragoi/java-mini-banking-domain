package service;

import exception.AccountNotFoundException;
import model.Account;
import model.Customer;
import model.SavingsAccount;
import util.IdGenerator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryBankService implements BankService {

    private final Map<Long, Account> accounts = new HashMap<>();

    @Override
    public Account createSavingsAccount(Customer customer) {
        long id = IdGenerator.nextId();
        Account account = new SavingsAccount(id, customer);
        accounts.put(id, account);
        return account;
    }

    @Override
    public void deposit(long accountId, double amount) {
        Account account = findAccount(accountId);
        account.deposit(amount);
    }

    @Override
    public void withdraw(long accountId, double amount) {
        Account account = findAccount(accountId);
        account.withdraw(amount);
    }

    @Override
    public void transfer(long fromId, long toId, double amount) {
        var from = findAccount(fromId);
        var to = findAccount(toId);

        from.withdraw(amount);
        to.deposit(amount);
    }

    private Account findAccount(long id) {
        Account account = accounts.get(id);
        if(account == null) {
            throw new AccountNotFoundException(id);
        }

        return account;
    }
}
