package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.model.response.ItemOrderDetails;

import java.text.DecimalFormat;

public class ItemOrderDetailsBuilderFactory {
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    public static ItemOrderDetails build (OrderRestaurantMenuEntity orderRestaurantMenuEntity) {
        return ItemOrderDetails.builder()
                .menuId(orderRestaurantMenuEntity.getMenuId())
                .name(orderRestaurantMenuEntity.getMenuEntity().getName())
                .pricePerItem(orderRestaurantMenuEntity.getPrice())
                .quantity(orderRestaurantMenuEntity.getQuantity())
                .totalPrice(Float.valueOf(decimalFormat.format(orderRestaurantMenuEntity.getPrice() * orderRestaurantMenuEntity.getQuantity().floatValue())))
                .orderStartTime(orderRestaurantMenuEntity.getOrderGivenTime())
                .orderEndTime(orderRestaurantMenuEntity.getOrderCompletionTime())
                .orderStatus(orderRestaurantMenuEntity.getOrderStatus())
                .build();
    }
}
