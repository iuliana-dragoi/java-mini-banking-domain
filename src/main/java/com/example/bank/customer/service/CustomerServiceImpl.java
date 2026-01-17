package com.example.bank.customer.service;

import com.example.bank.customer.model.Customer;
import com.example.bank.customer.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findById(long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer create(String name, String email) {
        Customer customer = new Customer(CustomerIdGenerator.nextId(), name, email);
        return customerRepository.save(customer);
    }
}
