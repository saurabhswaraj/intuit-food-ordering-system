package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.OrderStatus;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
public class GetorderDetailsResponse {
    Long orderId;
    List<RestaurantOrderDetails> restaurantOrderDetails;
    Float totalOrderPrice;
    ZonedDateTime orderStartTime;
    ZonedDateTime orderCompletionTime;
    OrderStatus orderStatus;
}
