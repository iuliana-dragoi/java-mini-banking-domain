package account.model;

public record Iban(String value) {

    public Iban {
        if (!isValid(value)) {
            throw new IllegalArgumentException("Invalid IBAN: " + value);
        }
    }

    private static boolean isValid(String iban) {
        return iban != null && iban.matches("RO\\d{22}");
    }
}
