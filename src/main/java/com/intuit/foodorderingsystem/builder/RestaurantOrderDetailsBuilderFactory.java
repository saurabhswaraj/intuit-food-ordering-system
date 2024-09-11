package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.response.ItemOrderDetails;
import com.intuit.foodorderingsystem.model.response.RestaurantOrderDetails;

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
                .restaurantType(restaurantEntity.getRestaurantType())
                .itemOrderDetailsList(itemOrderDetailsList)
                .totalRestaurantPrice(restaurantItemsPrice)
                .build();
    }
}
