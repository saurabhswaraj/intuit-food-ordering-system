package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.enums.FoodType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Item {
    String name;
    Float price;
    FoodType foodType;
}
