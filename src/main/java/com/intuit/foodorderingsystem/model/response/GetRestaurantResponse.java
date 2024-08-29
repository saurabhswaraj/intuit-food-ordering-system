package com.intuit.foodorderingsystem.model.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetRestaurantResponse {
    Long id;
    String name;
    String address;
    String city;
    String state;
    String pinCode;
    String contactNumber;
    Integer maxOrderCapacity;
}
