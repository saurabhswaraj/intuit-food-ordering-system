package com.intuit.foodorderingsystem.service.helper;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;

import java.util.List;

public interface RestaurantSelectionStrategy {

    List<RestaurantEntity> select(Long menuId, Integer quantity);
}
