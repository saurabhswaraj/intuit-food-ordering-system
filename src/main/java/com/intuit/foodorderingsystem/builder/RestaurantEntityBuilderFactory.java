package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;

public class RestaurantEntityBuilderFactory {

    public static RestaurantEntity build (CreateRestaurantRequest createRestaurantRequest) {
        return RestaurantEntity.builder()
                .address(createRestaurantRequest.getAddress())
                .isActive(true)
                .city(createRestaurantRequest.getCity())
                .name(createRestaurantRequest.getName())
                .state(createRestaurantRequest.getState())
                .pinCode(createRestaurantRequest.getPinCode())
                .contactNumber(createRestaurantRequest.getContactNumber())
                .restaurantType(createRestaurantRequest.getRestaurantType())
                .build();
    }
}
