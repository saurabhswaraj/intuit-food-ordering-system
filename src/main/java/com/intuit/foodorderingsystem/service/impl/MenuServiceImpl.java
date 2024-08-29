package com.intuit.foodorderingsystem.service.impl;


import com.intuit.foodorderingsystem.builder.CreateMenuResponseBuilderFactory;
import com.intuit.foodorderingsystem.builder.MenuEntityBuilderFactory;
import com.intuit.foodorderingsystem.builder.RestaurantMenuEntityBuilderFactory;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.FoodType;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.exception.IneligibleRequestException;
import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.request.Item;
import com.intuit.foodorderingsystem.model.response.CreateMenuResponse;
import com.intuit.foodorderingsystem.repository.MenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantMenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import com.intuit.foodorderingsystem.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Log4j2
public class MenuServiceImpl implements MenuService {

    private final RestaurantRepository  restaurantRepository;
    private final MenuRepository menuRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;

    @Override
    public CreateMenuResponse createMenu(CreateMenuRequest createMenuRequest) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndIsActiveTrue(createMenuRequest.getRestaurantId());
        if(restaurantEntity == null ) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST);
        }

        standardizeName(createMenuRequest.getItemList());

        List<Item> itemListFromRequest = createMenuRequest.getItemList();
        if (!checkIfAllItemsEligible(itemListFromRequest, restaurantEntity.getRestaurantType())) {
            throw new IneligibleRequestException(Messages.ALL_ITEMS_NOT_VEG);
        }

        if (hasRepeatedItem(itemListFromRequest)) {
            throw new IneligibleRequestException(Messages.HAS_REPEATED_ITEMS);
        }
        List<MenuEntity> menuEntities = menuRepository.findByNameIn(itemListFromRequest.stream()
                .map(item -> item.getName().toLowerCase()).toList());

        List<Item> itemListNotInDB = new LinkedList<>(itemListFromRequest.stream()
                .filter(item -> menuEntities.stream()
                        .noneMatch(menuEntity -> menuEntity.getName().equalsIgnoreCase(item.getName())))
                .toList());

        List<MenuEntity> menuEntitiesNotInDB = itemListNotInDB.stream().map(MenuEntityBuilderFactory::build).toList();
        List<MenuEntity> menuEntityList = menuRepository.saveAll(menuEntitiesNotInDB);

        menuEntityList.addAll(menuEntities);

        Map<String, Long> itemNameMenuIdMap = new HashMap<>();

        for (MenuEntity menuEntity : menuEntityList) {
            itemNameMenuIdMap.put(menuEntity.getName(), menuEntity.getId());
        }

        List<RestaurantMenuEntity> restaurantMenuEntities = itemListFromRequest.stream().map(item ->
            RestaurantMenuEntityBuilderFactory.build(itemNameMenuIdMap, createMenuRequest.getRestaurantId(), item)).toList();

        restaurantMenuRepository.saveAll(restaurantMenuEntities);

        return CreateMenuResponseBuilderFactory.build(createMenuRequest);
    }

    public Boolean hasRepeatedItem(List<Item> itemList) {
        Set<String> itemsSet = new HashSet<>();
        for (Item item: itemList) {
            if(itemsSet.contains(item.getName().toLowerCase())) {
                return true;
            }
            itemsSet.add(item.getName().toLowerCase());
        }
        return false;
    }


    public Boolean checkIfAllItemsEligible(List<Item> itemList, RestaurantType foodType) {
        if(RestaurantType.VEG == foodType) {
            boolean isNonVeg = itemList.stream().anyMatch(item -> item.getFoodType() != FoodType.VEG);
            return !isNonVeg;
        }
        return true;
    }

    public void standardizeName(List<Item> itemList) {
        for(Item item : itemList) {
            item.setName(StringUtils.normalizeSpace(item.getName().toLowerCase()));
        }
    }
}
