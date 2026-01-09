package service;

import model.Account;
import model.Customer;

import java.util.List;
import java.util.Map;

public interface AccountRepository {

    void addAccount(Customer customer, Account account);
    List<Account> getAccounts(Customer customer);
    Map<Customer, List<Account>> getAllAccounts();
}
