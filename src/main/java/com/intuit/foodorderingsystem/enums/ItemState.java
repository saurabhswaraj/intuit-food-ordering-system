package com.intuit.foodorderingsystem.enums;

public enum ItemState implements PersistableEnum<String> {
    IN_STOCK("IN_STOCK"),
    OUT_OF_STOCK("OUT_OF_STOCK");

    private final String value;
    ItemState(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return value;
    }

    @jakarta.persistence.Converter
    public static class Converter extends PersistableEnumConverter<ItemState, String> {
        public Converter() {
            super(ItemState.class);
        }
    }
}
