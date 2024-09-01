package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.OrdersEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.dto.ItemOrderDetails;
import com.intuit.foodorderingsystem.model.dto.RestaurantOrderDetails;
import com.intuit.foodorderingsystem.model.response.GetorderDetailsResponse;

import java.util.List;

public class GetOrderDetailsResponseBuilderFactory {
    public static GetorderDetailsResponse build (OrdersEntity ordersEntity, List<RestaurantOrderDetails> restaurantOrderDetailsList, Float totalOrderPrice) {
        return GetorderDetailsResponse.builder()
                .orderId(ordersEntity.getId())
                .restaurantOrderDetails(restaurantOrderDetailsList)
                .totalOrderPrice(totalOrderPrice)
                .orderStartTime(ordersEntity.getOrderGivenTime())
                .orderCompletionTime(ordersEntity.getOrderCompletionTime())
                .orderStatus(ordersEntity.getOrderStatus())
                .build();
    }
}
