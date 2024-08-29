package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.model.dto.CreateItem;
import com.intuit.foodorderingsystem.model.dto.Item;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateMenuRequest {
    Long restaurantId;

    List<CreateItem> itemList;
}
