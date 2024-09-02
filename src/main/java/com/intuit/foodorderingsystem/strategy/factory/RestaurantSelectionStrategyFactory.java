package com.intuit.foodorderingsystem.strategy.factory;

import com.intuit.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantSelectionStrategyFactory {

    private final RestaurantSelectionStrategyCache restaurantSelectionStrategyCache;
    private final ApplicationContext applicationContext;

    public RestaurantSelectionStrategy getObject() {
        String starategyBeanName = restaurantSelectionStrategyCache.getStrategyQualifierName();
        return (RestaurantSelectionStrategy)applicationContext.getBean(starategyBeanName);
    }
}
