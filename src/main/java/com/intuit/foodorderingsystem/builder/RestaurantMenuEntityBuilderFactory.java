package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.ItemState;
import com.intuit.foodorderingsystem.model.response.CreateItem;

import java.util.Map;

public class RestaurantMenuEntityBuilderFactory {

    public static RestaurantMenuEntity build (Map<String, Long> itemNameMenuIdMap, Long restaurantId, CreateItem item) {
        return RestaurantMenuEntity.builder()
                .restaurantId(restaurantId)
                .menuId(itemNameMenuIdMap.get(item.getName()))
                .price(item.getPrice())
                .itemState(ItemState.IN_STOCK)
                .build();
    }
}
