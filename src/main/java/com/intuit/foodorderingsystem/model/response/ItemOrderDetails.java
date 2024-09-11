package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ItemOrderDetails {

    private Long menuId;
    private String name;
    private Float pricePerItem;
    private Integer quantity;
    private Float totalPrice;
    private ZonedDateTime orderStartTime;
    private ZonedDateTime orderEndTime;
    private OrderStatus orderStatus;
}
