package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.response.CreateMenuResponse;

//This class will have many builder methods which will be overloaded
public class CreateMenuResponseBuilderFactory {

    public static CreateMenuResponse build (CreateMenuRequest createMenuRequests, Long restaurantId) {
        return CreateMenuResponse.builder()
                .restaurantId(restaurantId)
                .itemList(createMenuRequests.getItemList())
                .build();
    }
}
