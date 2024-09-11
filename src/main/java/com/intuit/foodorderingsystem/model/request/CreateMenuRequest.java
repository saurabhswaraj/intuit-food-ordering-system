package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.model.response.CreateItem;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class CreateMenuRequest {

    @NotEmpty(message = Messages.LIST_CAN_NOT_BE_EMPTY)
    private List<CreateItem> itemList;
}
