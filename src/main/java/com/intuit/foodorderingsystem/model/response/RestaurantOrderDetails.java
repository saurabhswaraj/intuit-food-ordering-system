package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.RestaurantType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class RestaurantOrderDetails {
    private Long restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantCity;
    private String restaurantState;
    private String restaurantPinCode;
    private RestaurantType restaurantType;
    private String restaurantContactNumber;
    private List<ItemOrderDetails> itemOrderDetailsList;
    private Float totalRestaurantPrice;

}
