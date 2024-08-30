package com.intuit.foodorderingsystem.service.helper;

import com.intuit.foodorderingsystem.enums.State;
import com.intuit.foodorderingsystem.repository.RestaurantStrategyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;


@Component
@RequiredArgsConstructor
@Log4j2
public class RestaurantSelectionStrategyCache {

    private final RestaurantStrategyRepository restaurantStrategyRepository;

    @Cacheable("strategyQualifierName")
    public String getStrategyQualifierName() {
        return restaurantStrategyRepository.findByState(State.ACTIVE).getStrategyQualifierName();
    }

    @CacheEvict(allEntries = true, value = {"strategyQualifierName"})
    @Scheduled(fixedDelay = 60 * 1000 ,  initialDelay = 500)
    public void reportCacheEvict() {
        log.info("Flushing cache :"+ ZonedDateTime.now());
    }
}
