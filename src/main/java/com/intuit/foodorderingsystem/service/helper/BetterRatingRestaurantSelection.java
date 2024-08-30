package com.intuit.foodorderingsystem.service.helper;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("BetterRatingRestaurantSelection")
@Log4j2
public class BetterRatingRestaurantSelection implements RestaurantSelectionStrategy{
    @Override
    public List<RestaurantEntity> select(Long menuId, Integer quantity) {
        log.info("Selected BetterRatingRestaurantSelection");
        return null;
    }
}
