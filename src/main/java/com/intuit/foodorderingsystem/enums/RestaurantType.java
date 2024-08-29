package com.intuit.foodorderingsystem.enums;

public enum RestaurantType implements PersistableEnum<String> {
    VEG("VEG"),
    NON_VEG("NON_VEG");

    private final String value;
    RestaurantType(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return value;
    }

    @jakarta.persistence.Converter
    public static class Converter extends PersistableEnumConverter<RestaurantType, String> {
        public Converter() {
            super(RestaurantType.class);
        }
    }
}
