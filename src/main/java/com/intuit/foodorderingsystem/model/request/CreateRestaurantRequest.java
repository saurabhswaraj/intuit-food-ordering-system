package com.intuit.foodorderingsystem.model.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRestaurantRequest {
    String name;
    String address;
    String city;
    String state;
    String pinCode;
    Integer maxOrderCapacity;
    String contactNumber;
}
