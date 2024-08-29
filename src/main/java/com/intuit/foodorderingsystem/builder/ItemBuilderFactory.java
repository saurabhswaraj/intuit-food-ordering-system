package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.model.dto.Item;

public class ItemBuilderFactory {

    public static Item build (RestaurantMenuEntity restaurantMenuEntity) {
        return Item.builder()
            .name(restaurantMenuEntity.getMenuEntity().getName())
            .foodType(restaurantMenuEntity.getMenuEntity().getFoodType())
            .menuId(restaurantMenuEntity.getMenuId())
            .price(restaurantMenuEntity.getPrice())
            .rating(restaurantMenuEntity.getRating())
                .itemState(restaurantMenuEntity.getItemState())
            .build();
    }
}
