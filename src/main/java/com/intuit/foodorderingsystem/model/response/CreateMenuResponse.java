package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.model.dto.CreateItem;
import com.intuit.foodorderingsystem.model.dto.Item;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateMenuResponse {
    Long restaurantId;

    List<CreateItem> itemList;
}
