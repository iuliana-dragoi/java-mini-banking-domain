package com.example.bank.account.service.impl;

import com.example.bank.account.model.*;
import com.example.bank.account.service.AccountFactory;
import com.example.bank.account.service.AccountIbanGenerator;
import com.example.bank.account.service.AccountIdGenerator;

public class AccountFactoryImpl implements AccountFactory {

    private final AccountIbanGenerator ibanGenerator;

    public AccountFactoryImpl(AccountIbanGenerator ibanGenerator) {
        this.ibanGenerator = ibanGenerator;
    }

    @Override
    public Account create(AccountType type, String ownerName, long customerId) {
        Iban iban = ibanGenerator.generate(type);
        long id = AccountIdGenerator.nextId();

        return switch (type) {
            case SAVINGS -> new SavingsAccount(id, iban.value(), ownerName, customerId);
            case BUSINESS -> new BusinessAccount(id, iban.value(), ownerName, customerId,50000);
            case CREDIT -> new CreditAccount(id, iban.value(), ownerName,  customerId,10000);
            case INVESTMENT -> new InvestmentAccount(id, iban.value(),ownerName, customerId);
            case PREMIUM -> new PremiumAccount(id, iban.value(), ownerName, customerId);
        };
    }
}
