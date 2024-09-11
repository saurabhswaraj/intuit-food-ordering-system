package com.intuit.foodorderingsystem.strategy.impl;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("BetterRatingRestaurantSelection")
@Log4j2
public class BetterRatingRestaurantSelection implements RestaurantSelectionStrategy {
    @Override
    public Map<Long, Integer> select(Long menuId, Integer quantity) {
        log.info("Selected BetterRatingRestaurantSelection");
        return null;
    }
}
