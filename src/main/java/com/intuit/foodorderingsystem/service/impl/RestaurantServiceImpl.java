package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.builder.CreateRestaurantResponseBuilderFactory;
import com.intuit.foodorderingsystem.builder.RestaurantCapacityEntityBuilderFactory;
import com.intuit.foodorderingsystem.builder.RestaurantEntityBuilderFactory;
import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.exception.AlreadyExistException;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;
import com.intuit.foodorderingsystem.repository.RestaurantCapacityRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import com.intuit.foodorderingsystem.service.RestaurantService;
import com.intuit.foodorderingsystem.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCapacityRepository restaurantCapacityRepository;
    private final TransactionUtil transactionUtil;

    @Override
    public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest createRestaurantRequest) {

        if(restaurantExist(createRestaurantRequest.getContactNumber())) {
            throw new AlreadyExistException("Restaurant already Exist");
        }
        RestaurantEntity restaurantEntity = RestaurantEntityBuilderFactory.build(createRestaurantRequest);
        RestaurantCapacityEntity restaurantCapacityEntity = transactionUtil.saveRestaurantDetails(restaurantEntity, createRestaurantRequest);
        return CreateRestaurantResponseBuilderFactory.build(restaurantEntity, restaurantCapacityEntity);
    }

    private Boolean restaurantExist(String contactNumber) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByContactNumber(contactNumber);
        return restaurantEntity != null;
    }


}
