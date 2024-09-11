package com.intuit.foodorderingsystem.service.impl;


import com.intuit.foodorderingsystem.builder.RestaurantCapacityEntityBuilderFactory;
import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.repository.RestaurantCapacityRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class RestaurantTransactionService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantCapacityRepository restaurantCapacityRepository;

    @Transactional
    public RestaurantCapacityEntity saveRestaurantDetails(RestaurantEntity restaurantEntity, CreateRestaurantRequest createRestaurantRequest) {
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        log.info("Restaurant saved with Id " + restaurantEntity.getId());
        RestaurantCapacityEntity restaurantCapacityEntity = RestaurantCapacityEntityBuilderFactory.build(
                createRestaurantRequest.getMaxOrderCapacity(), restaurantEntity.getId());
        restaurantCapacityEntity = restaurantCapacityRepository.save(restaurantCapacityEntity);
        return restaurantCapacityEntity;
    }
}
