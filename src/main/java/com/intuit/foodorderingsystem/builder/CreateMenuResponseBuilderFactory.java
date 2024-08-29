package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.response.CreateMenuResponse;

import java.util.List;

public class CreateMenuResponseBuilderFactory {

    public static CreateMenuResponse build (CreateMenuRequest createMenuRequests) {
        return CreateMenuResponse.builder()
                .restaurantId(createMenuRequests.getRestaurantId())
                .itemList(createMenuRequests.getItemList())
                .build();
    }
}
