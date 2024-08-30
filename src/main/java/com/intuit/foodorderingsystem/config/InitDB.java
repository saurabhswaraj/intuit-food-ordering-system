package com.intuit.foodorderingsystem.config;

import com.intuit.foodorderingsystem.entity.RestaurantStrategyEntity;
import com.intuit.foodorderingsystem.enums.State;
import com.intuit.foodorderingsystem.repository.RestaurantStrategyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class InitDB implements ApplicationRunner {

    private final RestaurantStrategyRepository restaurantStrategyRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = restaurantStrategyRepository.count();
        if(count == 0) {
            restaurantStrategyRepository.save(new RestaurantStrategyEntity(1L, "LowestCostRestaurantSelection", State.ACTIVE));
            restaurantStrategyRepository.save(new RestaurantStrategyEntity(2L, "BetterRatingRestaurantSelection", State.INACTIVE));
        }
    }
}
