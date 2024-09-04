package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.model.dto.CreateItem;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateMenuRequest {

    @NotEmpty(message = Messages.LIST_CAN_NOT_BE_EMPTY)
    List<CreateItem> itemList;
}
