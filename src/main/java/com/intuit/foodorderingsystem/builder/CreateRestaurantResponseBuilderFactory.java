package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.enums.State;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;

import java.util.stream.Collectors;

public class CreateRestaurantResponseBuilderFactory {

    public static CreateRestaurantResponse build (RestaurantEntity restaurantEntity, RestaurantCapacityEntity restaurantCapacityEntity) {
        return CreateRestaurantResponse.builder()
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .address(restaurantEntity.getAddress())
                .city(restaurantEntity.getCity())
                .state(restaurantEntity.getState())
                .pinCode(restaurantEntity.getPinCode())
                .contactNumber(restaurantEntity.getContactNumber())
                .maxOrderCapacity(restaurantCapacityEntity.getMaxCapacity())
                .build();
    }
}
