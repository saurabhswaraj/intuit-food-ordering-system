package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.response.CreateMenuResponse;

import java.util.List;

public class CreateMenuResponseBuilderFactory {

    public static CreateMenuResponse build (CreateMenuRequest createMenuRequest) {
        return CreateMenuResponse.builder()
                .restaurantId(createMenuRequest.getRestaurantId())
                .itemList(createMenuRequest.getItemList())
                .build();
    }
}
