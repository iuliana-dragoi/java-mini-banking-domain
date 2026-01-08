package service;

import model.Account;
import model.Customer;

public interface BankService {

    Account createSavingsAccount(Customer customer);

    void deposit(long accountId, double amount);

    void withdraw(long accountId, double amount);

    void transfer(long fromId, long toId, double amount);
}
