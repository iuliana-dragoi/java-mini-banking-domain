package customer.repository;

import account.model.Account;
import customer.model.Customer;
import java.util.List;

public interface CustomerRepository {

    Customer save(Customer customer);
    Customer findById(Long id);
    List<Customer> getAllCustomers();

    void addAccountToCustomer(Long customerId, Account account);
    List<Account> getCustomerAccounts(Long customerId);
}
