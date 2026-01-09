package model;

import java.time.Instant;

public record Transaction(TransactionType type, double amount, Instant instant) {

    public static Transaction deposit(double amount) {
        return new Transaction(TransactionType.DEPOSIT, amount, Instant.now());
    }

    public static Transaction withdraw(double amount) {
        return new Transaction(TransactionType.WITHDRAW, amount, Instant.now());
    }

    public static Transaction transfer(double amount) {
        return new Transaction(TransactionType.TRANSFER, amount, Instant.now());
    }

    public static Transaction applyInterest(double amount) {
        return new Transaction(TransactionType.INTEREST, amount, Instant.now());
    }

    public static Transaction applyBonusInterest(double amount) {
        return new Transaction(TransactionType.BONUS_INTEREST, amount, Instant.now());
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        INTEREST,
        BONUS_INTEREST
    };
}

