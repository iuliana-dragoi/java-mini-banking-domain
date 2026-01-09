package service;

import model.Account;
import model.Customer;

public interface AccountFactory {

    Account createSavingsAccount(Customer customer);
    Account createBusinessAccount(Customer customer);
    Account createCreditAccount(Customer customer);
    Account createInvestmentAccount(Customer customer);
    Account createPremiumAccount(Customer customer);
}
