package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.model.request.Item;

import java.util.List;
import java.util.Map;

public class RestaurantMenuEntityBuilderFactory {

    public static RestaurantMenuEntity build (Map<String, Long> itemNameMenuIdMap, Long restaurantId, Item item) {
        return RestaurantMenuEntity.builder()
                .restaurantId(restaurantId)
                .menuId(itemNameMenuIdMap.get(item.getName()))
                .price(item.getPrice())
                .build();
    }
}
