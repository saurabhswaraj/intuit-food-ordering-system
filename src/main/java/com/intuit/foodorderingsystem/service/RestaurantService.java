package com.intuit.foodorderingsystem.service;


import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;

public interface RestaurantService {

    CreateRestaurantResponse createRestaurant(CreateRestaurantRequest createRestaurantRequest);
}
