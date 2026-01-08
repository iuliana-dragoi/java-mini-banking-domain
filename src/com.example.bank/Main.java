import exception.BankException;
import model.*;
import service.BankService;
import service.InMemoryBankService;
import util.IdGenerator;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    BankService bank = new InMemoryBankService();

    Customer alice = new Customer(IdGenerator.nextId(), "Alice", "alice@test.com");
    Customer bob = new Customer(IdGenerator.nextId(), "Bob", "bob@test.com");
    Customer charlie = new Customer(IdGenerator.nextId(), "Charlie", "charlie@test.com");

    Account aliceSavings = new SavingsAccount(IdGenerator.nextId(), alice);
    Account bobBusiness = new BusinessAccount(IdGenerator.nextId(), bob, 500);
    Account charlieCredit = new CreditAccount(IdGenerator.nextId(), charlie, 1000);

    bank.addAccount(alice, aliceSavings);
    bank.addAccount(bob, bobBusiness);
    bank.addAccount(charlie, charlieCredit);

    try {
        aliceSavings.deposit(300);
        aliceSavings.withdraw(100);

        bobBusiness.deposit(200);
        bobBusiness.withdraw(600); // allowed, overdraft 500, sold 200 -> 600-200=400 < overdraft ok?

        charlieCredit.withdraw(800); // allowed, credit limit 1000
        charlieCredit.deposit(500);

    } catch (BankException e) {
        System.err.println("Error: " + e.getMessage());
    }

    Map<Customer, List<Account>> allAccounts = bank.getAllAccounts();

    allAccounts.forEach((customer, accounts) -> {
        System.out.println("Customer: " + customer.name());
        for (Account acc : accounts) {
            System.out.println("  Account #" + acc.getId() + " | Balance: " + acc.getBalance());
            System.out.println("    Total Deposits: " + Account.Statistics.totalDeposits(acc));
            System.out.println("    Total Withdrawals: " + Account.Statistics.totalWithdrawals(acc));
        }
        System.out.println("------------------------------------------------");
    });
}
