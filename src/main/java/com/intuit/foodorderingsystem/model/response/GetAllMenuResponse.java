package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.FoodType;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class GetAllMenuResponse {
    Long id;
    FoodType foodType;
    String name;
}
