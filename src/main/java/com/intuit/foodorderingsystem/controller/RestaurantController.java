package com.intuit.foodorderingsystem.controller;


import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;
import com.intuit.foodorderingsystem.model.response.BaseResponseModel;
import com.intuit.foodorderingsystem.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/restaurant")
@Log4j2
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    BaseResponseModel<CreateRestaurantResponse> createRestaurant (@RequestBody CreateRestaurantRequest createRestaurantRequest) {
        return new BaseResponseModel<>(restaurantService.createRestaurant(createRestaurantRequest));
    }
}
