package com.example.bank.customer.service;

import java.util.concurrent.atomic.AtomicLong;

public class CustomerIdGenerator {

    private static final AtomicLong SEQ = new AtomicLong(1);

    private CustomerIdGenerator() {}

    public static long nextId() {
        return SEQ.getAndIncrement();
    }

    public static void reset() {
        SEQ.set(1);
    }
}