package com.intuit.foodorderingsystem.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CreateMenuRequest {
    Long restaurantId;

    List<Item> itemList;
}
