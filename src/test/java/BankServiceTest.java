
import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;
import com.example.bank.account.repository.AccountRepository;
import com.example.bank.account.repository.AccountRepositoryImpl;
import com.example.bank.account.service.AccountFactory;
import com.example.bank.account.service.AccountIbanGenerator;
import com.example.bank.account.service.AccountService;
import com.example.bank.account.service.impl.AccountFactoryImpl;
import com.example.bank.account.service.impl.AccountServiceImpl;
import com.example.bank.account.service.impl.DefaultAccountIbanGenerator;
import com.example.bank.bank.service.BankService;
import com.example.bank.customer.model.Customer;
import com.example.bank.customer.repository.CustomerRepository;
import com.example.bank.customer.repository.CustomerRepositoryImpl;
import com.example.bank.customer.service.CustomerIdGenerator;
import com.example.bank.customer.service.CustomerService;
import com.example.bank.customer.service.CustomerServiceImpl;
import com.example.bank.transaction.repository.TransactionRepository;
import com.example.bank.transaction.repository.TransactionRepositoryImpl;
import com.example.bank.transaction.service.TransferService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankServiceTest {

    BankService bankService;
    CustomerService customerService;

    @BeforeEach
    void setUp() {
        AccountRepository accountRepo = new AccountRepositoryImpl();
        CustomerRepository customerRepo = new CustomerRepositoryImpl();
        TransactionRepository transactionRepo = new TransactionRepositoryImpl();
        TransferService transferService = new TransferService(accountRepo, transactionRepo);

        AccountIbanGenerator accountIbanGenerator = new DefaultAccountIbanGenerator();
        AccountFactory accountFactory = new AccountFactoryImpl(accountIbanGenerator);
        customerService = new CustomerServiceImpl(customerRepo);

        AccountService accountService = new AccountServiceImpl(
                accountRepo,
                accountFactory,
                transactionRepo,
                transferService
        );

        bankService = new BankService(accountService, customerService);

        CustomerIdGenerator.reset();
    }

    @Test
    public void openAccountForCustomer() {
        Customer customer = customerService.create("Iuliana", "iuliana@test.com");
        Account account = bankService.openAccountForCustomer(customer.id(), "Iuliana", AccountType.SAVINGS);

        assertNotNull(customer);
        assertNotNull(account);
        assertEquals(1L, account.getCustomerId());
        assertEquals(AccountType.SAVINGS, account.getType());
    }

    @Test
    public void createCustomerWithAccount() {
        Account account = bankService.createCustomerWithAccount("Iuliana", "iuliana@test.com", AccountType.BUSINESS);

        assertNotNull(account);
        assertEquals(1L, account.getCustomerId());
    }

    @AfterEach
    public void tearDown() {
        bankService = null;
        customerService = null;
    }
}
