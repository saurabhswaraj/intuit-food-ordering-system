package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.model.request.Item;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateMenuResponse {
    Long restaurantId;

    List<Item> itemList;
}
