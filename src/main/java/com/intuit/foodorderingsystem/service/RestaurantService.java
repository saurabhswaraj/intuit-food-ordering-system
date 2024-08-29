package com.intuit.foodorderingsystem.service;


import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.request.EditRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;
import com.intuit.foodorderingsystem.model.response.EditRestaurantResponse;
import com.intuit.foodorderingsystem.model.response.EmptyResponse;
import com.intuit.foodorderingsystem.model.response.GetRestaurantResponse;

import java.util.List;

public interface RestaurantService {

    CreateRestaurantResponse createRestaurant(CreateRestaurantRequest createRestaurantRequest);

    List<GetRestaurantResponse> getAllRestaurants ();

    GetRestaurantResponse getSingleRestaurant(Long restaurantId);

    EditRestaurantResponse editRestaurants(Long restaurantId, EditRestaurantRequest editRestaurantRequest);

    EmptyResponse deactivateRestaurants(Long restaurantId);
}
