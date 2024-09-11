package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class ChangeItemsStateMenuRequest {

    @NotEmpty(message = Messages.LIST_CAN_NOT_BE_EMPTY)
    private List<Long> itemListToChangeState;
}
