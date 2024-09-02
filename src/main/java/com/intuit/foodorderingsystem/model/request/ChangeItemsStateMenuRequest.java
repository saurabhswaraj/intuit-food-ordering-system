package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Builder
@Value
public class ChangeItemsStateMenuRequest {
    @NotEmpty(message = Messages.LIST_CAN_NOT_BE_EMPTY)
    List<Long> itemListToChangeState;
}
