package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.ItemState;
import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.model.dto.CreateItem;
import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;

import java.time.ZonedDateTime;
import java.util.Map;

public class OrderRestaurantMenuEntityBuilderFactory {

    public static OrderRestaurantMenuEntity build (Long orderId, CreateOrderRequest createOrderRequest, Integer quantity, Long restaurantId, Float price) {
        return OrderRestaurantMenuEntity.builder()
                .menuId(createOrderRequest.getMenuId())
                .orderGivenTime(ZonedDateTime.now())
                .orderId(orderId)
                .orderStatus(OrderStatus.PROCESSING)
                .quantity(quantity)
                .restaurantId(restaurantId)
                .price(price * quantity.floatValue())
                .build();
    }
}
