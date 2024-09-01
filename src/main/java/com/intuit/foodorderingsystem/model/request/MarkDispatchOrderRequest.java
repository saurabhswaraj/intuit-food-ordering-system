package com.intuit.foodorderingsystem.model.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MarkDispatchOrderRequest {
    Long orderId;
    Long restaurantId;
}
