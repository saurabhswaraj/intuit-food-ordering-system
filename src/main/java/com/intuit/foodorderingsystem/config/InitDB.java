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
    public void run(ApplicationArguments args) {
        long count = restaurantStrategyRepository.count();
        if(count == 0) {
            restaurantStrategyRepository.save(RestaurantStrategyEntity.builder()
                            .id(1L)
                            .strategyQualifierName("LowestCostRestaurantSelection")
                            .state(State.ACTIVE)
                                    .build());
            restaurantStrategyRepository.save(RestaurantStrategyEntity.builder()
                    .id(2L)
                    .strategyQualifierName("BetterRatingRestaurantSelection")
                    .state(State.INACTIVE)
                    .build());
        }
    }
}
