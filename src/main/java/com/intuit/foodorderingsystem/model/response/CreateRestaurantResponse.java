package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.RestaurantType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRestaurantResponse {
    Long id;
    String name;
    String address;
    String city;
    String state;
    String pinCode;
    String contactNumber;
    Integer maxOrderCapacity;
    RestaurantType restaurantType;
}
