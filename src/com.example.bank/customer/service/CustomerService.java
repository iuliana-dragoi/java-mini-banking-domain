package customer.service;

import customer.model.Customer;

public interface CustomerService {

    Customer findById(long id);

    Customer create(String name, String email);
}
