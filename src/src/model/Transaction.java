package model;

import java.time.LocalDateTime;

public class Transaction {

    private final TransactionType type;
    private final double amount;
    private final LocalDateTime timestamp;

    private Transaction(TransactionType type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    };

    public static Transaction deposit(double amount) {
        return new Transaction(TransactionType.DEPOSIT, amount);
    }

    public static Transaction withdraw(double amount) {
        return new Transaction(TransactionType.WITHDRAW, amount);
    }

    @Override
    public String toString() {
        return type + " " + amount + " at " + timestamp;
    }
}

