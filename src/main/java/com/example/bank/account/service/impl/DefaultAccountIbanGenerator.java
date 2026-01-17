package com.example.bank.account.service.impl;

import com.example.bank.account.model.AccountType;
import com.example.bank.account.model.Iban;
import com.example.bank.account.service.AccountIbanGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class DefaultAccountIbanGenerator implements AccountIbanGenerator {

    private static final AtomicLong SEQ = new AtomicLong(1000000);

    @Override
    public Iban generate(AccountType type) {

        String bankCode = switch (type) {
            case SAVINGS -> "AAA";
            case BUSINESS -> "BBB";
            case CREDIT -> "CCC";
            case INVESTMENT -> "DDD";
            case PREMIUM -> "EEE";
        };

        long unique = SEQ.getAndIncrement();
        String ibanValue = "RO49" + bankCode + String.format("%017d", unique);

        return new Iban(ibanValue);
    }
}
