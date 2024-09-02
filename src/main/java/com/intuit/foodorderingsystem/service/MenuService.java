package com.intuit.foodorderingsystem.service;


import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.request.DeleteItemsMenuRequest;
import com.intuit.foodorderingsystem.model.request.ChangeItemsStateMenuRequest;
import com.intuit.foodorderingsystem.model.response.*;

import java.util.List;

public interface MenuService {

    CreateMenuResponse createMenu (Long restaurantId, CreateMenuRequest createMenuRequest);

    List<GetAllMenuResponse> getAllItems();

    GetRestaurantMenuResponse getRestaurantMenu (Long restaurantId);

    EmptyResponse deleteItemFromMenu (Long restaurantId, DeleteItemsMenuRequest deleteItemsMenuRequest);

    EmptyResponse outOfStockItemFromMenu (Long restaurantId, ChangeItemsStateMenuRequest changeItemsStateMenuRequest);

    EmptyResponse inStockItemFromMenu (Long restaurantId, ChangeItemsStateMenuRequest changeItemsStateMenuRequest);
}
