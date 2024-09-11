package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.response.GetRestaurantMenuResponse;

public class GetRestaurantMenuResponseBuilderFactory {

    public static GetRestaurantMenuResponse build (Long restaurantId, RestaurantEntity restaurantEntity) {

        return GetRestaurantMenuResponse.builder()
                .restaurantId(restaurantId)
                .menu(restaurantEntity.getRestaurantMenuEntities().stream()
                        .map(ItemBuilderFactory::build).toList())
                .build();
    }
}
