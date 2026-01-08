package model;

import java.time.Instant;

public record Transaction(TransactionType type, double amount, Instant instant) {

    public static Transaction deposit(double amount) {
        return new Transaction(TransactionType.DEPOSIT, amount, Instant.now());
    }

    public static Transaction withdraw(double amount) {
        return new Transaction(TransactionType.WITHDRAW, amount, Instant.now());
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER
    };
}

