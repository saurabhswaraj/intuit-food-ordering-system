package com.intuit.foodorderingsystem.service.impl;


import com.intuit.foodorderingsystem.builder.*;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.FoodType;
import com.intuit.foodorderingsystem.enums.ItemState;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.exception.IneligibleRequestException;
import com.intuit.foodorderingsystem.model.request.ChangeItemsStateMenuRequest;
import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.request.DeleteItemsMenuRequest;
import com.intuit.foodorderingsystem.model.response.*;
import com.intuit.foodorderingsystem.repository.MenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantMenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import com.intuit.foodorderingsystem.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
@Log4j2
public class MenuServiceImpl implements MenuService {

    private final RestaurantRepository  restaurantRepository;
    private final MenuRepository menuRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;

    @Override
    public CreateMenuResponse createMenu(Long restaurantId, CreateMenuRequest createMenuRequest) {
        RestaurantEntity restaurantEntity = getActiveRestaurantById(restaurantId);
        standardizeName(createMenuRequest.getItemList());

        List<CreateItem> itemListFromRequest = createMenuRequest.getItemList();
        if (!checkIfAllItemsEligible(itemListFromRequest, restaurantEntity.getRestaurantType())) {
            throw new IneligibleRequestException(Messages.ALL_ITEMS_NOT_VEG);
        }

        if (hasRepeatedItem(itemListFromRequest)) {
            throw new IneligibleRequestException(Messages.HAS_REPEATED_ITEMS);
        }
        List<MenuEntity> menuEntities = menuRepository.findByNameIn(itemListFromRequest.stream()
                .map(item -> item.getName().toLowerCase()).toList());

        List<CreateItem> itemListNotInDB = new LinkedList<>(itemListFromRequest.stream()
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
            RestaurantMenuEntityBuilderFactory.build(itemNameMenuIdMap, restaurantId, item)).toList();

        restaurantMenuRepository.saveAll(restaurantMenuEntities);

        return CreateMenuResponseBuilderFactory.build(createMenuRequest, restaurantId);
    }

    @Override
    public List<GetAllMenuResponse> getAllItems(Integer pageNo, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<MenuEntity> menuEntityList = menuRepository.findAll(pageRequest);
        return menuEntityList.stream().map(GetAllMenuResponseBuilderFactory::build).toList();
    }

    @Override
    public GetRestaurantMenuResponse getRestaurantMenu(Long restaurantId) {
        RestaurantEntity restaurantEntity = getActiveRestaurantById(restaurantId);
        return GetRestaurantMenuResponseBuilderFactory.build(restaurantId, restaurantEntity);
    }

    @Override
    public EmptyResponse deleteItemFromMenu(Long restaurantId, DeleteItemsMenuRequest deleteItemsMenuRequest) {
        getActiveRestaurantById(restaurantId);

        List<RestaurantMenuEntity> restaurantMenuEntities = restaurantMenuRepository
                .findAllByRestaurantIdAndMenuIdIn(restaurantId, deleteItemsMenuRequest.getItemListToDelete());

        restaurantMenuRepository.deleteAll(restaurantMenuEntities);
        return new EmptyResponse();
    }

    @Override
    public EmptyResponse outOfStockItemFromMenu(Long restaurantId, ChangeItemsStateMenuRequest changeItemsStateMenuRequest) {
        getActiveRestaurantById(restaurantId);

        List<RestaurantMenuEntity> restaurantMenuEntities = restaurantMenuRepository
                .findAllByRestaurantIdAndMenuIdIn(restaurantId, changeItemsStateMenuRequest.getItemListToChangeState());

        restaurantMenuEntities.forEach(restaurantMenuEntity -> restaurantMenuEntity.setItemState(ItemState.OUT_OF_STOCK));
        restaurantMenuRepository.saveAll(restaurantMenuEntities);

        return new EmptyResponse();
    }

    @Override
    public EmptyResponse inStockItemFromMenu(Long restaurantId, ChangeItemsStateMenuRequest changeItemsStateMenuRequest) {
        getActiveRestaurantById(restaurantId);

        List<RestaurantMenuEntity> restaurantMenuEntities = restaurantMenuRepository
                .findAllByRestaurantIdAndMenuIdIn(restaurantId, changeItemsStateMenuRequest.getItemListToChangeState());

        restaurantMenuEntities.forEach(restaurantMenuEntity -> restaurantMenuEntity.setItemState(ItemState.IN_STOCK));
        restaurantMenuRepository.saveAll(restaurantMenuEntities);

        return new EmptyResponse();
    }

    private Boolean hasRepeatedItem(List<CreateItem> itemList) {
        Set<String> itemsSet = new HashSet<>();
        for (CreateItem item: itemList) {
            if(itemsSet.contains(item.getName().toLowerCase())) {
                return true;
            }
            itemsSet.add(item.getName().toLowerCase());
        }
        return false;
    }


    private Boolean checkIfAllItemsEligible(List<CreateItem> itemList, RestaurantType foodType) {
        if(RestaurantType.VEG == foodType) {
            boolean isNonVeg = itemList.stream().anyMatch(item -> item.getFoodType() != FoodType.VEG);
            return !isNonVeg;
        }
        return true;
    }

    private void standardizeName(List<CreateItem> itemList) {
        for(CreateItem item : itemList) {
            item.setName(StringUtils.normalizeSpace(item.getName().toLowerCase()));
        }
    }

    private RestaurantEntity getActiveRestaurantById(Long restaurantId){
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findByIdAndIsActiveTrue(restaurantId);
        if(restaurantEntityOptional.isEmpty() ) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST_OR_DISABLED);
        }
        return restaurantEntityOptional.get();
    }
}
