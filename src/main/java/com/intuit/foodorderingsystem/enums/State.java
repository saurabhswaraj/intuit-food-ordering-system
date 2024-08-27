package com.intuit.foodorderingsystem.enums;

import org.springframework.data.domain.Persistable;

public enum State implements PersistableEnum<String> {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;
    State(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return value;
    }

    @jakarta.persistence.Converter
    public static class Converter extends PersistableEnumConverter<State, String> {
        public Converter() {
            super(State.class);
        }
    }
}
