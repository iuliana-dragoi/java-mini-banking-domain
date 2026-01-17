package com.example.bank.bank.service;

public class CalcuatorServiceImpl implements CalcuatorService {

    @Override
    public int add(int a, int b) {
        return a+b;
    }

    @Override
    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    @Override
    public boolean isEven(int a) {
        return a % 2 == 0;
    }
}
