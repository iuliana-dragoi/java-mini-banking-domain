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

    bank.createSavingsAccount(alice);
    bank.createBusinessAccount(alice);
    bank.createCreditAccount(alice);
    bank.createInvestmentAccount(alice);
    bank.createPremiumAccount(alice);

    Account savings = bank.getAccounts(alice).stream()
            .filter(a -> a.getType() == AccountType.SAVINGS)
            .findFirst().orElseThrow();

    bank.deposit(savings.getId(), 1000);

    Thread t1 = new Thread(() -> {
       savings.withdraw(500);
        System.out.println("Thread 1 done, balance: " + savings.getBalance());
    });

    Thread t2 = new Thread(() -> {
        savings.withdraw(50);
        System.out.println("Thread 2 done, balance: " + savings.getBalance());
    });

    t1.start();
    t2.start();

    t1.join();
    t2.join();


    System.out.println("Final Balance: " + savings.getBalance());

    printAccount(bank, alice);
}

void printAccount(BankService bank, Customer customer) {
    System.out.println();
    List<Account> accounts = bank.getAccounts(customer);
    accounts.forEach(a -> {
        System.out.println(   a.getClass().getSimpleName() + " -> " + a);
    });
}