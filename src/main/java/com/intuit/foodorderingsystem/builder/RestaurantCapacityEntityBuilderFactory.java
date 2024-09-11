package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.enums.State;

public class RestaurantCapacityEntityBuilderFactory {

    public static RestaurantCapacityEntity build (Integer maxCapacity, Long restaurantId) {
        return RestaurantCapacityEntity.builder()
                .maxCapacity(maxCapacity)
                .restaurantId(restaurantId)
                .currentCapacity(0)
                .state(State.ACTIVE)
                .build();
    }
}
