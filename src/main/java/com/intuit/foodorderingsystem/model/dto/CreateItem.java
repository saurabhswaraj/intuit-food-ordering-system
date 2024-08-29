package com.intuit.foodorderingsystem.model.dto;

import com.intuit.foodorderingsystem.enums.FoodType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateItem {
    String name;
    Float price;
    FoodType foodType;
}
