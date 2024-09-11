package com.intuit.foodorderingsystem.strategy;

import java.util.Map;

public interface RestaurantSelectionStrategy {

    Map<Long, Integer> select(Long menuId, Integer quantity);
}
