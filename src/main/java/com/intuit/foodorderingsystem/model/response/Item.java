package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.FoodType;
import com.intuit.foodorderingsystem.enums.ItemState;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Item {
    private Long menuId;
    private String name;
    private Float price;
    private FoodType foodType;
    private Float rating;
    private ItemState itemState;
}
