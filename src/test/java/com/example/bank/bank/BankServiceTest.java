package com.example.bank.bank;

import com.example.bank.account.model.Account;
import com.example.bank.account.model.AccountType;
import com.example.bank.account.model.SavingsAccount;
import com.example.bank.account.service.AccountService;
import com.example.bank.bank.service.BankService;
import com.example.bank.customer.model.Customer;
import com.example.bank.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private CustomerService customerService;

    @Spy
    @InjectMocks
    BankService bankService;


    @Test
    public void openAccountForCustomer() {
        Customer customer = new Customer(1L, "John Doe", "john@test.com");
        Account account = new SavingsAccount(10L, AccountType.SAVINGS.name(), "John Doe", 1L);

        when(customerService.findById(1L)).thenReturn(customer);
        when(accountService.openAccount(AccountType.SAVINGS, "John Doe", 1L)).thenReturn(account);

        Account result = bankService.openAccountForCustomer(1L, "John Doe", AccountType.SAVINGS);

        assertEquals(AccountType.SAVINGS, result.getType());
        verify(customerService).findById(1L);
        verify(accountService).openAccount(AccountType.SAVINGS, "John Doe", 1L);
    }

    @Test
    public void createCustomerWithAccount() {
        Customer customer = new Customer(1L, "Ana", "ana@test.com");
        Account account = new SavingsAccount(20L, AccountType.SAVINGS.name(), "Ana", 1L);

        when(customerService.create("Ana", "ana@test.com")).thenReturn(customer);
        when(accountService.openAccount(AccountType.SAVINGS, "Ana", 1L)).thenReturn(account);

        Account result = bankService.createCustomerWithAccount("Ana", "ana@test.com", AccountType.SAVINGS);

        assertEquals(1L, result.getCustomerId());
        verify(customerService).create("Ana", "ana@test.com");
        verify(accountService).openAccount(AccountType.SAVINGS, "Ana", 1L);
    }
}
