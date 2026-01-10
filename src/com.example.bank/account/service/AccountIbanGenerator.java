package account.service;

import account.model.AccountType;
import account.model.Iban;

public interface AccountIbanGenerator {

    Iban generate(AccountType type);
}
