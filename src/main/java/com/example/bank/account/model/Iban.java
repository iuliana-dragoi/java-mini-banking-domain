package com.example.bank.account.model;

public record Iban(String value) {

    public Iban {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("IBAN cannot be null or empty");
        }

        String normalized = normalize(value);

        if (!isValidFormat(normalized)) {
            throw new IllegalArgumentException("Invalid IBAN format: " + value);
        }
    }

    private String normalize(String iban) {
        return iban.replaceAll("\\s+", "").toUpperCase();
    }

    private boolean isValidFormat(String iban) {
        return iban.length() == 24
                && iban.startsWith("RO")
                && iban.substring(2, 4).matches("\\d{2}")
                && iban.substring(4).matches("[A-Z0-9]{20}");
    }
}
