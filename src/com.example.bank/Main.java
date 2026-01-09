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
    InMemoryBankService test = new InMemoryBankService();

    Customer alice = new Customer(IdGenerator.nextId(), "Alice", "alice@test.com");

    bank.createSavingsAccount(alice);
    bank.createBusinessAccount(alice);
    bank.createCreditAccount(alice);
    bank.createInvestmentAccount(alice);
    bank.createPremiumAccount(alice);

    printAccount(bank, alice);
}

void printAccount(BankService bank, Customer customer) {
    List<Account> accounts = bank.getAccounts(customer);
    accounts.forEach(a -> {
        System.out.println(   a.getClass().getSimpleName() + " -> " + a);
    });
}