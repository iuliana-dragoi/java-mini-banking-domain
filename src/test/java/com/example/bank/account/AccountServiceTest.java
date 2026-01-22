package com.example.bank.account;

import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;
import com.example.bank.account.repository.AccountRepository;
import com.example.bank.account.repository.AccountRepositoryImpl;
import com.example.bank.account.service.AccountFactory;
import com.example.bank.account.service.AccountIbanGenerator;
import com.example.bank.account.service.AccountIdGenerator;
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
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class AccountServiceTest {

    AccountService accountService;
    BankService bankService;
    Account account1;
    Account account2;

    @BeforeEach
    public void setUp() {
        AccountRepository accountRepo = new AccountRepositoryImpl();
        CustomerRepository customerRepo = new CustomerRepositoryImpl();
        TransactionRepository transactionRepo = new TransactionRepositoryImpl();
        TransferService transferService = new TransferService(accountRepo, transactionRepo);

        AccountIbanGenerator accountIbanGenerator = new DefaultAccountIbanGenerator();
        AccountFactory accountFactory = new AccountFactoryImpl(accountIbanGenerator);
        CustomerService customerService = new CustomerServiceImpl(customerRepo);

        accountService = new AccountServiceImpl(
                accountRepo,
                accountFactory,
                transactionRepo,
                transferService
        );

        bankService = new BankService(accountService, customerService);

        account1 = bankService.createCustomerWithAccount("Alice", "alice@test.com", AccountType.SAVINGS);
        account2 = bankService.createCustomerWithAccount("Bob", "bob@test.com", AccountType.SAVINGS);
        accountService.deposit(account1.getId(), 500, "Initial deposit");
        accountService.deposit(account2.getId(), 600, "Initial deposit");
    }

    @Test
    @Order(1)
    public void deposit() {
        accountService.deposit(account1.getId(), 500, "Initial deposit");
        accountService.deposit(account2.getId(), 400, "Initial deposit");

        assertNotNull(account1);
        assertNotNull(account2);
        assertEquals(1000, account1.getBalance());
        assertEquals(1000, account2.getBalance());
    }

    @Test
    @Order(2)
    public void withdraw() {
        accountService.withdraw(account1.getId(), 200, "Initial deposit");
        accountService.withdraw(account2.getId(), 600, "Initial deposit");
        assertEquals(300, account1.getBalance());
        assertEquals(0, account2.getBalance());
    }

    @Test
    @Order(3)
    public void transfer() {
        accountService.transfer(account1.getId(), account2.getId(), 100, "Test Description");
        assertEquals(400, account1.getBalance());
        assertEquals(700, account2.getBalance());
    }

    @Test
    @Order(4)
    public void openAccount() {
        Customer customer = new Customer(3, "Ana", "ana@test.com");
        Account account = accountService.openAccount(AccountType.SAVINGS, "Ana", customer.id());
        assertNotNull(customer);
        assertNotNull(account);
        assertEquals(3, account.getId());
    }

    @Test
    @Order(5)
    public void findByCustomerId() {
        List<Account> account = accountService.findByCustomerId(account1.getCustomerId());
        assertEquals(1, account.size());
        assertNotNull(account.get(0));
        assertEquals(500, account.get(0).getBalance());
    }

    @BeforeEach
    public void tearUp() {
        accountService = null;
        bankService = null;
        account1 = null;
        account2 = null;
        DefaultAccountIbanGenerator.reset();
        CustomerIdGenerator.reset();
        AccountIdGenerator.reset();
    }
}
