package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.model.dto.CreateItem;
import com.intuit.foodorderingsystem.model.dto.Item;

public class MenuEntityBuilderFactory {

    public static MenuEntity build (CreateItem item) {
        return MenuEntity.builder()
                .name(item.getName())
                .foodType(item.getFoodType())
                .build();
    }
}
