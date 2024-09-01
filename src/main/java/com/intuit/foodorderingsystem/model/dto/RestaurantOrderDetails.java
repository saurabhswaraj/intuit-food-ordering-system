package com.intuit.foodorderingsystem.model.dto;

import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RestaurantOrderDetails {
    Long restaurantId;
    String restaurantName;
    String restaurantAddress;
    String restaurantCity;
    String restaurantState;
    String restaurantPinCode;
    RestaurantType restaurantType;
    String restaurantContactNumber;
    List<ItemOrderDetails> itemOrderDetailsList;
    Float totalRestaurantPrice;

}
