package model;

public class Customer {

    private final long id;
    private String name;
    private String email;

    public Customer(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    private long getId() {
        return id;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getEmail() {
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Customer other)) return false;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }

    @Override
    public String toString() {
        return "Customer{id=%d, name='%s'}".formatted(id, name);
    }
}
