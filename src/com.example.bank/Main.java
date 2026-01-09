import exception.BankException;
import model.*;
import service.BankService;
import service.InMemoryBankService;
import util.IdGenerator;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() throws InterruptedException {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.

    BankService bank = new InMemoryBankService();
    Customer alice = new Customer(IdGenerator.nextId(), "Alice", "alice@test.com");
    Customer bob = new Customer(IdGenerator.nextId(), "Bob", "bob@test.com");

    Account aliceSavings = bank.createSavingsAccount(alice);
    Account bobSavings = bank.createSavingsAccount(bob);

    bank.deposit(aliceSavings.getId(), 1000);
    bank.deposit(bobSavings.getId(), 550);

    System.out.println("Initial Balances:");
    printAccounts(bank, alice);
    printAccounts(bank, bob);

    int numOperations = 100;
    int poolSize = 5;
    Random rand = new Random();

    ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
    for(int i = 0; i < numOperations; i++) {

        executorService.submit(() -> {
            int op = rand.nextInt(3);
            double amount = rand.nextInt(200) + 1;

            try{
                switch (op) {
                    case 0 -> {
                        aliceSavings.deposit(amount);
                    }
                    case 1 -> {
                        aliceSavings.withdraw(amount);
                    }
                    case 2 -> {
                        bank.transfer(aliceSavings.getId(), bobSavings.getId(), amount);
                    }
                }

            }
            catch (Exception e) {
                System.out.printf("[Thread %s] Operation error: %s%n", Thread.currentThread().getName(), e.getMessage());
            }
        });
    }

    executorService.shutdown();
    executorService.awaitTermination(1, TimeUnit.MINUTES);

    printAccounts(bank, alice);
    printAccounts(bank, bob);
}

void execute() {

}

void printAccounts(BankService bank, Customer customer) {
    System.out.println();
    List<Account> accounts = bank.getAccounts(customer);
    accounts.forEach(a -> {
        System.out.println(   a.getClass().getSimpleName() + " -> " + a);
    });
}