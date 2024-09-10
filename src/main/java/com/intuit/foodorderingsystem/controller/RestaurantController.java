package com.intuit.foodorderingsystem.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.request.EditRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.*;
import com.intuit.foodorderingsystem.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
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
    BaseResponseModel<List<GetRestaurantResponse>> getAllRestaurant (@RequestParam(defaultValue = "0") Integer pageNo,
                                                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return new BaseResponseModel<>(restaurantService.getAllRestaurants(pageNo, pageSize));
    }

    @GetMapping("{restaurantId}")
    BaseResponseModel<GetRestaurantResponse> getSingleRestaurant (@PathVariable Long restaurantId) {
        return new BaseResponseModel<>(restaurantService.getSingleRestaurant(restaurantId));
    }

    @PatchMapping ("{restaurantId}")
    BaseResponseModel<EditRestaurantResponse> editRestaurant (@PathVariable Long restaurantId,
                                                              @Valid @RequestBody String editRestaurantRequestString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        EditRestaurantRequest editRestaurantRequest = objectMapper.readValue(editRestaurantRequestString, EditRestaurantRequest.class);
        return new BaseResponseModel<>(restaurantService.editRestaurants(restaurantId, editRestaurantRequest));
    }

    @PatchMapping ("/deactivate/{restaurantId}")
    BaseResponseModel<EmptyResponse> deactivateRestaurant (@PathVariable Long restaurantId) {
        return new BaseResponseModel<>(restaurantService.deactivateRestaurants(restaurantId));
    }

    @PatchMapping ("/activate/{restaurantId}")
    BaseResponseModel<EmptyResponse> activateRestaurant (@PathVariable Long restaurantId) {
        return new BaseResponseModel<>(restaurantService.activateRestaurants(restaurantId));
    }
}
