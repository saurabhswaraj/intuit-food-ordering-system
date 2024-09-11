package com.intuit.foodorderingsystem.model.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class GetRestaurantMenuResponse {

    Long restaurantId;
    List<Item> menu;
}
