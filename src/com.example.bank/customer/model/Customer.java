package customer.model;

public record Customer(long id, String name, String email, boolean vip) {

    public Customer(long id, String name, String email) {
        this(id, name, email, false);
    }

    public Customer {
        if(name == null || email == null) {
            throw new IllegalArgumentException("Name and Email must not be null!");
        }
    }
}