package exception;

public class InsufficientFundException extends BankException {
    
    public InsufficientFundException(String message) {
        super(message);
    }
}
