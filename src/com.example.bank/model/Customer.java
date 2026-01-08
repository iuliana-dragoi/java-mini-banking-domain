package model;

public record Customer(long id, String name, String email) {

    public Customer {
        if(name == null || email == null) {
            throw new IllegalArgumentException("Name and Email must not be null!");
        }
    }
}