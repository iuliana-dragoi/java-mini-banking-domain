package account.service.impl;

import account.model.*;
import account.service.AccountFactory;
import account.service.AccountIbanGenerator;
import account.service.AccountIdGenerator;

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
