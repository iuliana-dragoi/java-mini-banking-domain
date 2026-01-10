import account.model.Account;
import account.repository.AccountRepository;
import account.repository.AccountRepositoryImpl;
import account.service.AccountFactory;
import account.service.AccountIbanGenerator;
import account.service.AccountService;
import account.service.impl.AccountFactoryImpl;
import account.service.impl.AccountServiceImpl;
import account.service.impl.DefaultAccountIbanGenerator;
import bank.service.BankService;
import customer.repository.CustomerRepository;
import customer.repository.CustomerRepositoryImpl;
import customer.service.CustomerService;
import customer.service.CustomerServiceImpl;
import transaction.repository.TransactionRepository;
import transaction.repository.TransactionRepositoryImpl;
import transaction.service.TransferService;

void main() throws InterruptedException {

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

    // todo











//    BankService bank = new InMemoryBankService();
//    Customer alice = new Customer(AccountIdGenerator.nextId(), "Alice", "alice@test.com");
//    Customer bob = new Customer(AccountIdGenerator.nextId(), "Bob", "bob@test.com");
//
//    Account aliceSavings = bank.createSavingsAccount(alice);
//    Account bobSavings = bank.createSavingsAccount(bob);
//
//    bank.deposit(aliceSavings.getId(), 1000);
//    bank.deposit(bobSavings.getId(), 550);
//
//    System.out.println("Initial Balances:");
//    printAccounts(bank, alice);
//    printAccounts(bank, bob);
//
//    System.out.println();
//    int numOperations = 100;//1_000_000;
//    int poolSize = 15;
//    Random rand = new Random();
//
//    ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
//    for(int i = 0; i < numOperations; i++) {
//
//        executorService.submit(() -> {
//            int op = rand.nextInt(3);
//            double amount = rand.nextInt(200) + 1;
//
//            try{
//                switch (op) {
//                    case 0 -> {
//                        aliceSavings.deposit(amount);
//                    }
//                    case 1 -> {
//                        aliceSavings.withdraw(amount);
//                    }
//                    case 2 -> {
////                        bank.transfer(aliceSavings.getId(), bobSavings.getId(), amount); // TODO
//                    }
//                }
//
//            }
//            catch (Exception e) {
//                System.out.printf("[Thread %s] Operation error: %s%n", Thread.currentThread().getName(), e.getMessage());
//            }
//        });
//    }
//
//    executorService.shutdown();
//    executorService.awaitTermination(1, TimeUnit.MINUTES);
//
//    System.out.println();
//    printAccounts(bank, alice);
//    printAccounts(bank, bob);
//
//    boolean ok = BankAuditService.verifyAllAccounts(bank.getAllAccounts().values().stream().flatMap(List::stream).toList());
//    System.out.println("All accounts consistent? " + ok);
}
