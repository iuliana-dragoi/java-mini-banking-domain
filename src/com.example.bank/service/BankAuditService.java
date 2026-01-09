package service;

import model.Account;
import model.Transaction;

import java.util.List;

public class BankAuditService {

    public static boolean verifyAccount(Account account) {

        double totalDeposits = account.getTransactions().stream()
                .filter(t -> t.type() == Transaction.TransactionType.DEPOSIT)
                .mapToDouble(Transaction::amount)
                .sum();

        double totalWithdrawals = account.getTransactions().stream()
                .filter(t -> t.type() == Transaction.TransactionType.WITHDRAW)
                .mapToDouble(Transaction::amount)
                .sum();

        double totalTransfersIn = account.getTransactions().stream()
                .filter(t -> t.type() == Transaction.TransactionType.TRANSFER)
                .mapToDouble(Transaction::amount)
                .sum();

        double expectedBalance = totalDeposits - totalWithdrawals + totalTransfersIn;

        boolean consistent = Math.abs(account.getBalance() - expectedBalance) < 0.0001;
        if(!consistent) {
            System.out.println("Inconsistency found in account " + account.getId() +
                    ": balance = " + account.getBalance() +
                    ", expected = " + expectedBalance);
        }

        return consistent;
    }

    public static boolean verifyAllAccounts(List<Account> accounts) {
        return accounts.stream().allMatch(BankAuditService::verifyAccount);
    }
}
