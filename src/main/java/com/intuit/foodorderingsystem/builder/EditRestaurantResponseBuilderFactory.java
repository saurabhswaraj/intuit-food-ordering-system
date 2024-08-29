package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;
import com.intuit.foodorderingsystem.model.response.EditRestaurantResponse;


public class EditRestaurantResponseBuilderFactory {

    public static EditRestaurantResponse build (RestaurantEntity restaurantEntity) {
        return EditRestaurantResponse.builder()
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .address(restaurantEntity.getAddress())
                .city(restaurantEntity.getCity())
                .state(restaurantEntity.getState())
                .pinCode(restaurantEntity.getPinCode())
                .contactNumber(restaurantEntity.getContactNumber())
                .restaurantType(restaurantEntity.getRestaurantType())
                .build();
    }
}
