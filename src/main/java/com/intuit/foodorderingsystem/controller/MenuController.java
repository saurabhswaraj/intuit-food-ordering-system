package com.intuit.foodorderingsystem.controller;

import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.request.DeleteItemsMenuRequest;
import com.intuit.foodorderingsystem.model.request.ChangeItemsStateMenuRequest;
import com.intuit.foodorderingsystem.model.response.*;
import com.intuit.foodorderingsystem.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@Log4j2
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping({"{restaurantId}"})
    BaseResponseModel<CreateMenuResponse> createMenu (@PathVariable Long restaurantId, @Valid @RequestBody CreateMenuRequest createMenuRequest) {
        return new BaseResponseModel<>(menuService.createMenu(restaurantId, createMenuRequest));
    }

    @GetMapping("/all")
    BaseResponseModel<List<GetAllMenuResponse>> getAllMenu(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        return new BaseResponseModel<>(menuService.getAllItems(pageNo, pageSize));
    }

    @GetMapping({"{restaurantId}"})
    BaseResponseModel<GetRestaurantMenuResponse> getRestaurantMenu(@PathVariable Long restaurantId) {
        return new BaseResponseModel<>(menuService.getRestaurantMenu(restaurantId));
    }

    @DeleteMapping({"{restaurantId}"})
    BaseResponseModel<EmptyResponse> deleteItemFromMenu(@PathVariable Long restaurantId, @RequestBody DeleteItemsMenuRequest deleteItemsMenuRequest) {
        return new BaseResponseModel<>(menuService.deleteItemFromMenu(restaurantId, deleteItemsMenuRequest));
    }

    @PatchMapping({"{restaurantId}/out-of-stock"})
    BaseResponseModel<EmptyResponse> makeItemOutOfStockFromMenu(@PathVariable Long restaurantId, @RequestBody ChangeItemsStateMenuRequest changeItemsStateMenuRequest) {
        return new BaseResponseModel<>(menuService.outOfStockItemFromMenu(restaurantId, changeItemsStateMenuRequest));
    }

    @PatchMapping({"{restaurantId}/in-stock"})
    BaseResponseModel<EmptyResponse> makeItemInStockFromMenu(@PathVariable Long restaurantId, @RequestBody ChangeItemsStateMenuRequest changeItemsStateMenuRequest) {
        return new BaseResponseModel<>(menuService.inStockItemFromMenu(restaurantId, changeItemsStateMenuRequest));
    }
}
