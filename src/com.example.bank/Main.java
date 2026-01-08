import exception.BankException;
import model.Customer;
import service.BankService;
import service.InMemoryBankService;
import util.IdGenerator;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    BankService service = new InMemoryBankService();

    var ana = new Customer(IdGenerator.nextId(), "Ana", "ana@test.com");
    var ion = new Customer(IdGenerator.nextId(), "Ion", "ion@test.com");

    var account1 = service.createSavingsAccount(ana);
    var account2 = service.createSavingsAccount(ion);

    try {
        service.deposit(account1.getId(), 100);
        service.deposit(account2.getId(), 500);
        service.withdraw(account1.getId(), 50);
        service.transfer(account1.getId(), account2.getId(), 20);

    }
    catch (BankException e) {
        System.out.println("Business error: " + e.getMessage());
    }
    finally {
        System.out.println("End of operations");
    }

    System.out.println(account1);
    System.out.println(account2);
}
