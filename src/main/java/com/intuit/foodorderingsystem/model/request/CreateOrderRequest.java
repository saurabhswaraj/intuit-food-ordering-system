package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class CreateOrderRequest {

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    private Long menuId;

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    private Integer quantity;
}
