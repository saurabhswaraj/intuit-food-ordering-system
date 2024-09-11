package com.intuit.foodorderingsystem.model.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateMenuResponse {
    Long restaurantId;

    List<CreateItem> itemList;
}
