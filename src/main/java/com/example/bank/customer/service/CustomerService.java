package com.example.bank.customer.service;

import com.example.bank.customer.model.Customer;

public interface CustomerService {

    Customer findById(long id);

    Customer create(String name, String email);
}
