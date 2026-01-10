package account.service;

import account.model.Account;
import account.model.AccountType;

public interface AccountFactory {

    Account create(AccountType type, String ownerName, long customerId);
}
