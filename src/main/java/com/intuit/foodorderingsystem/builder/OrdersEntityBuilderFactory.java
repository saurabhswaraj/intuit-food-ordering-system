package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.entity.OrdersEntity;
import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.model.dto.CreateItem;

import java.time.ZonedDateTime;
import java.util.Map;

public class OrdersEntityBuilderFactory {

    public static OrdersEntity build (Long userId) {
        return OrdersEntity.builder()
                .orderGivenTime(ZonedDateTime.now())
                .orderStatus(OrderStatus.PROCESSING)
                .userId(userId)
                .build();
    }
}
