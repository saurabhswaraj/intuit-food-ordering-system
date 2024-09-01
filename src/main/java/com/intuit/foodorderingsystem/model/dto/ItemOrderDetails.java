package com.intuit.foodorderingsystem.model.dto;

import com.intuit.foodorderingsystem.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
public class ItemOrderDetails {

    Long menuId;
    String name;
    Float pricePerItem;
    Integer quantity;
    Float totalPrice;
    ZonedDateTime orderStartTime;
    ZonedDateTime orderEndTime;
    OrderStatus orderStatus;
}
