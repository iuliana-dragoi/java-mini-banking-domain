package com.example.bank;

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
import com.example.bank.customer.service.CustomerService;
import com.example.bank.customer.service.CustomerServiceImpl;
import com.example.bank.transaction.repository.TransactionRepository;
import com.example.bank.transaction.repository.TransactionRepositoryImpl;
import com.example.bank.transaction.service.TransferService;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        AccountRepository accountRepo = new AccountRepositoryImpl();
        CustomerRepository customerRepo = new CustomerRepositoryImpl();
        TransactionRepository transactionRepo = new TransactionRepositoryImpl();
        TransferService transferService = new TransferService(accountRepo, transactionRepo);

        AccountIbanGenerator accountIbanGenerator = new DefaultAccountIbanGenerator();
        AccountFactory accountFactory = new AccountFactoryImpl(accountIbanGenerator);
        CustomerService customerService = new CustomerServiceImpl(customerRepo);

        AccountService accountService = new AccountServiceImpl(
                accountRepo,
                accountFactory,
                transactionRepo,
                transferService
        );

        BankService bankService = new BankService(accountService, customerService);

        // ----- ----- ----- -----

        System.out.println("1. Creating new customer with account...");
        Account aliceAccount = bankService.createCustomerWithAccount(
                "Alice",
                "alice@test.com",
                AccountType.SAVINGS
        );
        System.out.println("   ✓ Created: " + aliceAccount);

        // ----- ----- ----- -----

        System.out.println("\n2. Creating customer and opening account separately...");
        Customer bob = customerService.create("Bob", "bob@test.com");
        System.out.println("   ✓ Customer created: " + bob.name());

        Account bobSavings = bankService.openAccountForCustomer(
                bob.id(),
                bob.name(),
                AccountType.SAVINGS
        );
        System.out.println("   ✓ Account created: " + bobSavings.getIban());

        // ----- ----- ----- -----

        Account bobBusiness = bankService.openAccountForCustomer(
                bob.id(),
                bob.name(),
                AccountType.BUSINESS
        );
        System.out.println("   ✓ Business account: " + bobBusiness.getIban());

        // ----- ----- ----- -----

        System.out.println("\n3. Making initial deposits...");
        accountService.deposit(aliceAccount.getId(), 1000.0, "Initial deposit");
        accountService.deposit(bobSavings.getId(), 550.0, "Initial deposit");
        accountService.deposit(bobBusiness.getId(), 5000.0, "Business funding");

        System.out.println("   Alice balance: " + aliceAccount.getBalance());
        System.out.println("   Bob savings: " + bobSavings.getBalance());
        System.out.println("   Bob business: " + bobBusiness.getBalance());

        // ----- ----- ----- -----

        System.out.println("\n4. Performing regular operations...");

        // Withdrawal
        accountService.withdraw(aliceAccount.getId(), 200.0, "ATM withdrawal");
        System.out.println("   Alice after withdrawal: " +
                accountRepo.findById(aliceAccount.getId()).getBalance());

        // Transfer
        transferService.transfer(
                bobBusiness.getId(),
                bobSavings.getId(),
                1000.0,
                "Transfer from business to savings"
        );
        System.out.println("   Bob business after transfer: " +
                accountRepo.findById(bobBusiness.getId()).getBalance());
        System.out.println("   Bob savings after transfer: " +
                accountRepo.findById(bobSavings.getId()).getBalance());


        // ----- ----- ----- -----

        System.out.println("\n5. Running concurrent operations stress test...");

        int numOperations = 5;
        int poolSize = 15;
        Random rand = new Random();

        double aliceInitialBalance = accountRepo.findById(aliceAccount.getId()).getBalance();
        double bobSavingsInitialBalance = accountRepo.findById(bobSavings.getId()).getBalance();

        ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

        for (int i = 0; i < numOperations; i++) {
            executorService.submit(() -> {
                int op = rand.nextInt(3);
                double amount = rand.nextInt(50) + 1; // Smaller amounts to reduce failures

                try {
                    switch (op) {
                        case 0 -> {
                            // Deposit to Alice
                            accountService.deposit(
                                    aliceAccount.getId(),
                                    amount,
                                    "Concurrent deposit"
                            );
                        }
                        case 1 -> {
                            // Withdraw from Alice
                            accountService.withdraw(
                                    aliceAccount.getId(),
                                    amount,
                                    "Concurrent withdrawal"
                            );
                        }
                        case 2 -> {
                            // Transfer Alice -> Bob
                            transferService.transfer(
                                    aliceAccount.getId(),
                                    bobSavings.getId(),
                                    amount,
                                    "Concurrent transfer"
                            );
                        }
                    }
                } catch (IllegalStateException e) {
                    // Expected - some operations will fail due to insufficient funds
                } catch (Exception e) {
                    System.err.println("Unexpected error: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);

        if (finished) {
            System.out.println("   ✓ Completed " + numOperations + " concurrent operations");
        }

        // ----- ----- ----- -----

        System.out.println("\n6. Final balances:");
        printAccountDetails(accountRepo, aliceAccount.getId());
        printAccountDetails(accountRepo, bobSavings.getId());
        printAccountDetails(accountRepo, bobBusiness.getId());

        // ----- ----- ----- -----
    }


    public static void printAccountDetails(AccountRepository accountRepo, Long accountId) {
        Account account = accountRepo.findById(accountId);
        System.out.printf("   %s [%s]: %.2f%n",
                account.getClass().getSimpleName(),
                account.getIban(),
                account.getBalance()
        );
    }
}