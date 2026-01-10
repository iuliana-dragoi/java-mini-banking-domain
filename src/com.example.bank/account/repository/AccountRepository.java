package account.repository;

import account.model.Account;
import customer.model.Customer;

import java.util.List;
import java.util.Map;

public interface AccountRepository {

    Account save(Account account);
    Account findById(Long id);
    Account findByIban(String iban);
    List<Account> findAll();
    void delete(Long id);
    List<Account> findByCustomerId(Long customerId);
}
