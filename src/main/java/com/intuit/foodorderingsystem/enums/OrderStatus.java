package com.intuit.foodorderingsystem.enums;

public enum OrderStatus implements PersistableEnum<String> {
    PROCESSING("PROCESSING"),
    DISPATCHED("DISPATCHED");

    private final String value;
    OrderStatus(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return value;
    }

    @jakarta.persistence.Converter
    public static class Converter extends PersistableEnumConverter<OrderStatus, String> {
        public Converter() {
            super(OrderStatus.class);
        }
    }
}
