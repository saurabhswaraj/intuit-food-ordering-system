package com.intuit.foodorderingsystem.enums;

public enum FoodType implements PersistableEnum<String> {
    VEG("VEG"),
    NON_VEG("NON_VEG");

    private final String value;
    FoodType(String value) {
        this.value = value;
    }

    @Override
    public String getCode() {
        return value;
    }

    @jakarta.persistence.Converter
    public static class Converter extends PersistableEnumConverter<FoodType, String> {
        public Converter() {
            super(FoodType.class);
        }
    }
}
