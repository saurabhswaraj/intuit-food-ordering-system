package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.request.Item;

public class MenuEntityBuilderFactory {

    public static MenuEntity build (Item item) {
        return MenuEntity.builder()
                .name(item.getName())
                .foodType(item.getFoodType())
                .build();
    }
}
