package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.model.dto.ItemOrderDetails;

public class ItemOrderDetailsBuilderFactory {
    public static ItemOrderDetails build (OrderRestaurantMenuEntity orderRestaurantMenuEntity) {
        return ItemOrderDetails.builder()
                .menuId(orderRestaurantMenuEntity.getMenuId())
                .name(orderRestaurantMenuEntity.getMenuEntity().getName())
                .pricePerItem(orderRestaurantMenuEntity.getPrice())
                .quantity(orderRestaurantMenuEntity.getQuantity())
                .totalPrice(orderRestaurantMenuEntity.getPrice() * orderRestaurantMenuEntity.getQuantity().floatValue())
                .orderStartTime(orderRestaurantMenuEntity.getOrderGivenTime())
                .orderEndTime(orderRestaurantMenuEntity.getOrderCompletionTime())
                .orderStatus(orderRestaurantMenuEntity.getOrderStatus())
                .build();
    }
}
