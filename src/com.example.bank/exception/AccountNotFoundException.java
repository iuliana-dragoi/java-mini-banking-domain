package exception;

public class AccountNotFoundException extends BankException {

    public AccountNotFoundException(long id) {
        super("Account not found: " + id);
    }
}
