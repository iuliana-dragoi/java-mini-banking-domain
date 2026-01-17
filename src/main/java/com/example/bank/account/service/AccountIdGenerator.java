package com.example.bank.account.service;

import java.util.concurrent.atomic.AtomicLong;

public class AccountIdGenerator {

    private static final AtomicLong SEQ = new AtomicLong(1);

    private AccountIdGenerator() {}

    public static long nextId() {
        return SEQ.getAndIncrement();
    }

    public static void reset() {
        SEQ.set(1);
    }
}
