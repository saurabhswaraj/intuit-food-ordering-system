package com.intuit.foodorderingsystem.entity;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@EqualsAndHashCode
public class RestaurantMenuCompositeKey implements Serializable {

    private Long restaurantId;
    private Long menuId;

}
