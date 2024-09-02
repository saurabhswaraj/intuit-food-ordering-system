package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.model.dto.CreateItem;
import com.intuit.foodorderingsystem.model.dto.Item;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateMenuRequest {

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    Long restaurantId;

    @NotEmpty(message = Messages.LIST_CAN_NOT_BE_EMPTY)
    List<CreateItem> itemList;
}
