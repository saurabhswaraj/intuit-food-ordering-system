package com.intuit.foodorderingsystem.controller;


import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.request.EditRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.*;
import com.intuit.foodorderingsystem.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@Log4j2
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    BaseResponseModel<CreateRestaurantResponse> createRestaurant (@Valid @RequestBody CreateRestaurantRequest createRestaurantRequest) {
        return new BaseResponseModel<>(restaurantService.createRestaurant(createRestaurantRequest));
    }

    @GetMapping("/all")
    BaseResponseModel<List<GetRestaurantResponse>> getAllRestaurant () {
        return new BaseResponseModel<>(restaurantService.getAllRestaurants());
    }

    @GetMapping("{restaurantId}")
    BaseResponseModel<GetRestaurantResponse> getSingleRestaurant (@PathVariable Long restaurantId) {
        return new BaseResponseModel<>(restaurantService.getSingleRestaurant(restaurantId));
    }

    @PatchMapping ("{restaurantId}")
    BaseResponseModel<EditRestaurantResponse> editRestaurant (@PathVariable Long restaurantId,
                                                              @RequestBody EditRestaurantRequest editRestaurantRequest) {
        return new BaseResponseModel<>(restaurantService.editRestaurants(restaurantId, editRestaurantRequest));
    }

    @PatchMapping ("/deactivate/{restaurantId}")
    BaseResponseModel<EmptyResponse> deactivateRestaurant (@PathVariable Long restaurantId) {
        return new BaseResponseModel<>(restaurantService.deactivateRestaurants(restaurantId));
    }
}
