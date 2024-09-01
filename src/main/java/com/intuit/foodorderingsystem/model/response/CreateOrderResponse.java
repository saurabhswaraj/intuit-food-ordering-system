package com.intuit.foodorderingsystem.model.response;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class CreateOrderResponse {
    Long orderId;
}
