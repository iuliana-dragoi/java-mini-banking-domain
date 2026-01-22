package com.example.bank.account;

import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;
import com.example.bank.account.model.BusinessAccount;
import com.example.bank.account.model.SavingsAccount;
import com.example.bank.account.repository.AccountRepository;
import com.example.bank.account.service.AccountFactory;
import com.example.bank.account.service.impl.AccountServiceImpl;
import com.example.bank.customer.model.Customer;
import com.example.bank.transaction.repository.TransactionRepository;
import com.example.bank.transaction.service.TransferService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountFactory factory;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransferService transferService;

    @InjectMocks
    AccountServiceImpl accountService;

    Account account1;
    Account account2;

    @BeforeEach
    public void setUp() {
        account1 = new SavingsAccount(1L, "IBAN1", "Alice", 1L);
        account2 = new SavingsAccount(2L, "IBAN2", "Bob", 2L);

        account1.deposit(1000);
        account2.deposit(1000);

        lenient().when(accountRepository.findById(1L)).thenReturn(account1);
        lenient().when(accountRepository.findById(2L)).thenReturn(account2);
        lenient().when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    @Order(1)
    public void deposit() {
        accountService.deposit(1L, 1000, "Deposit Alice");
        accountService.deposit(2L, 1000, "Deposit Bob");

        assertEquals(2000, account1.getBalance());
        assertEquals(2000, account2.getBalance());

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).findById(2L);
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    @Order(2)
    public void withdraw() {
        accountService.withdraw(1L, 200, "Withdraw Alice");
        accountService.withdraw(2L, 600, "Withdraw Bob");

        assertEquals(800, account1.getBalance());
        assertEquals(400, account2.getBalance());

        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).findById(2L);
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    @Order(3)
    public void transfer() {

        doAnswer(invocation -> {
            long fromId = invocation.getArgument(0);
            long toId = invocation.getArgument(1);
            double amount = invocation.getArgument(2);
            String desc = invocation.getArgument(3);

            Account from = accountRepository.findById(fromId);
            Account to = accountRepository.findById(toId);

            from.withdraw(amount);
            to.deposit(amount);

            accountRepository.save(from);
            accountRepository.save(to);

            return null;
        }).when(transferService).transfer(anyLong(), anyLong(), anyDouble(), anyString());

        accountService.transfer(1L, 2L, 300, "Alice -> Bob");

        assertEquals(700, account1.getBalance());
        assertEquals(1300, account2.getBalance());

        verify(transferService, times(1)).transfer(1L, 2L, 300, "Alice -> Bob");
        verify(accountRepository, times(1)).save(account1);
        verify(accountRepository, times(1)).save(account2);
    }

    @Test
    @Order(4)
    public void openAccount() {
        Customer customer = new Customer(3, "Ana", "ana@test.com");
        Account newAccount = new SavingsAccount(3L, "IBAN3", "Ana", customer.id());

        when(factory.create(AccountType.SAVINGS, "Ana", customer.id())).thenReturn(newAccount);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Account account = accountService.openAccount(AccountType.SAVINGS, "Ana", customer.id());

        assertNotNull(account);
        assertEquals(3L, account.getId());
        assertEquals("Ana", account.getOwnerName());
        assertEquals(AccountType.SAVINGS, account.getType());

        verify(factory, times(1)).create(AccountType.SAVINGS, "Ana", customer.id());
        verify(accountRepository, times(1)).save(newAccount);
    }

    @Test
    @Order(5)
    public void findByCustomerId() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new SavingsAccount(3L, "IBAN1", "Alice", 4L));
        accounts.add(new BusinessAccount(4L, "IBAN2", "Bob", 4L, 1000));

        when(accountRepository.findByCustomerId(4L)).thenReturn(accounts);

        List<Account> result = accountService.findByCustomerId(4L);

        verify(accountRepository, times(1)).findByCustomerId(4L);
        assertEquals(2, result.size());
        assertNotNull(result.get(0));
        assertNotNull(result.get(1));
    }
}
