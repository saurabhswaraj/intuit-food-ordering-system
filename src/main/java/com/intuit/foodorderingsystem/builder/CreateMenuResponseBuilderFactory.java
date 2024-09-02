package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.response.CreateMenuResponse;

import java.util.List;

public class CreateMenuResponseBuilderFactory {

    public static CreateMenuResponse build (CreateMenuRequest createMenuRequests, Long restaurantId) {
        return CreateMenuResponse.builder()
                .restaurantId(restaurantId)
                .itemList(createMenuRequests.getItemList())
                .build();
    }
}
