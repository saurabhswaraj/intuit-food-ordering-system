package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;
import com.intuit.foodorderingsystem.model.response.GetRestaurantResponse;


public class GetRestaurantResponseBuilderFactory {

    public static GetRestaurantResponse build (RestaurantEntity restaurantEntity, RestaurantCapacityEntity restaurantCapacityEntity) {
        return GetRestaurantResponse.builder()
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
