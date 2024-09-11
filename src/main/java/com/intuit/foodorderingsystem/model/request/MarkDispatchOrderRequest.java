package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class MarkDispatchOrderRequest {

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    private Long orderId;

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    private Long restaurantId;
}
