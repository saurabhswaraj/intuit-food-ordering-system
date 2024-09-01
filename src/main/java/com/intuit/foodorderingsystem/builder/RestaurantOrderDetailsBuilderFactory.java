package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.dto.ItemOrderDetails;
import com.intuit.foodorderingsystem.model.dto.RestaurantOrderDetails;

import java.util.List;

public class RestaurantOrderDetailsBuilderFactory {
    public static RestaurantOrderDetails build (RestaurantEntity restaurantEntity, List<ItemOrderDetails> itemOrderDetailsList, Float restaurantItemsPrice) {
        return RestaurantOrderDetails.builder()
                .restaurantId(restaurantEntity.getId())
                .restaurantName(restaurantEntity.getName())
                .restaurantAddress(restaurantEntity.getAddress())
                .restaurantCity(restaurantEntity.getCity())
                .restaurantState(restaurantEntity.getState())
                .restaurantPinCode(restaurantEntity.getPinCode())
                .restaurantContactNumber(restaurantEntity.getContactNumber())
                .itemOrderDetailsList(itemOrderDetailsList)
                .totalRestaurantPrice(restaurantItemsPrice)
                .build();
    }
}
