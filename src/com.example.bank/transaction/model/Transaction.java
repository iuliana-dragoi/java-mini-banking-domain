package transaction.model;

import java.time.Instant;

public record Transaction(
        Long id,
        Long fromAccountId,
        Long toAccountId,
        double amount,
        TransactionType type,
        Instant instant,
        String description) {

    public static Transaction forDeposit(Long accountId, double amount, String description) {
        return new Transaction(
                null,
                null,
                accountId,
                amount,
                TransactionType.DEPOSIT,
                Instant.now(),
                description
        );
    }

    public static Transaction forWithdraw(Long accountId, double amount, String description) {
        return new Transaction(
                null,
                null,
                accountId,
                amount,
                TransactionType.WITHDRAW,
                Instant.now(),
                description
        );
    }

    public static Transaction forTransfer(Long fromAccountId, Long toAccountId, double amount, String description) {
        return new Transaction(
                null,
                fromAccountId,
                toAccountId,
                amount,
                TransactionType.TRANSFER,
                Instant.now(),
                description
        );
    }

    public static Transaction forInterest(Long accountId, double amount, String description) {
        return new Transaction(
                null,
                null,
                accountId,
                amount,
                TransactionType.INTEREST,
                Instant.now(),
                description
        );
    }

    public boolean involvesAccount(long accountId) {
        return (fromAccountId != null && fromAccountId.equals(accountId)) ||
                (toAccountId != null && toAccountId.equals(accountId));
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        INTEREST,
        BONUS_INTEREST
    };
}

