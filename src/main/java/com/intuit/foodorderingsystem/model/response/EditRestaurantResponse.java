package com.intuit.foodorderingsystem.model.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EditRestaurantResponse {
    Long id;
    String name;
    String address;
    String city;
    String state;
    String pinCode;
    Integer maxOrderCapacity;
    String contactNumber;
}
