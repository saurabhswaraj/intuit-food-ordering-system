package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MarkDispatchOrderRequest {
    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    Long orderId;

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    Long restaurantId;
}
