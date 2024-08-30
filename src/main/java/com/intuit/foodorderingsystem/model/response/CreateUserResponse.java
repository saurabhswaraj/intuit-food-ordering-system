package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.RestaurantType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserResponse {
    Long id;
    String name;
    String address;
    String city;
    String state;
    String pinCode;
    Boolean isActive;
    String contactNumber;
}
