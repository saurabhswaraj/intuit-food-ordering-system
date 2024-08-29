package com.intuit.foodorderingsystem.model.dto;

import com.intuit.foodorderingsystem.enums.FoodType;
import com.intuit.foodorderingsystem.enums.ItemState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Item {
    Long menuId;
    String name;
    Float price;
    FoodType foodType;
    Float rating;
    ItemState itemState;
}
