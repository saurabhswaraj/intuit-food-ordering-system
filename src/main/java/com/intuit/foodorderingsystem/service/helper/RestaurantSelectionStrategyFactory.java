package com.intuit.foodorderingsystem.service.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
