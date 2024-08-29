package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.FoodType;
import com.intuit.foodorderingsystem.model.dto.Item;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
public class GetRestaurantMenuResponse {

    Long restaurantId;
    List<Item> menu;
}
