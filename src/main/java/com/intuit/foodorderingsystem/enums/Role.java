package com.intuit.foodorderingsystem.enums;

public enum Role implements PersistableEnum<String> {
    USER("USER"),
    RESTAURANT("RESTAURANT");

    private final String value;
    Role(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return value;
    }

    @jakarta.persistence.Converter
    public static class Converter extends PersistableEnumConverter<Role, String> {
        public Converter() {
            super(Role.class);
        }
    }
}
