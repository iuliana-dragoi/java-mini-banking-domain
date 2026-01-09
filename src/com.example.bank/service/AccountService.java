package service;

public interface AccountService {

    void deposit(long accountId, double amount);
    void withdraw(long accountId, double amount);
    void transfer(long fromId, long toId, double amount);
}
