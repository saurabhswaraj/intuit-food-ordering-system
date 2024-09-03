package com.intuit.foodorderingsystem.service.impl;


import com.intuit.foodorderingsystem.builder.RestaurantCapacityEntityBuilderFactory;
import com.intuit.foodorderingsystem.entity.RestaurantAuthenticationEntity;
import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.repository.RestaurantAuthenticationRepository;
import com.intuit.foodorderingsystem.repository.RestaurantCapacityRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class RestaurantTransactionService {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantAuthenticationRepository restaurantAuthenticationRepository;

    private final RestaurantCapacityRepository restaurantCapacityRepository;

    @Transactional
    public RestaurantCapacityEntity saveRestaurantDetails(RestaurantEntity restaurantEntity, CreateRestaurantRequest createRestaurantRequest) {
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        log.info(restaurantEntity);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(createRestaurantRequest.getPassword());

        RestaurantAuthenticationEntity restaurantAuthenticationEntity = RestaurantAuthenticationEntity.builder()
                .id(restaurantEntity.getId())
                .encryptedPassword(encodedPassword)
                .build();
        restaurantAuthenticationRepository.save(restaurantAuthenticationEntity);

        RestaurantCapacityEntity restaurantCapacityEntity = RestaurantCapacityEntityBuilderFactory.build(
                createRestaurantRequest.getMaxOrderCapacity(), restaurantEntity.getId());
        restaurantCapacityEntity = restaurantCapacityRepository.save(restaurantCapacityEntity);
        return restaurantCapacityEntity;
    }
}
