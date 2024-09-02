package com.intuit.foodorderingsystem.strategy.impl;

import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.State;
import com.intuit.foodorderingsystem.repository.RestaurantMenuRepository;
import com.intuit.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service("LowestCostRestaurantSelection")
@Log4j2
@RequiredArgsConstructor
public class LowestCostRestaurantSelection implements RestaurantSelectionStrategy {

    private final RestaurantMenuRepository restaurantMenuRepository;

    @Override
    public List<RestaurantEntity> select(Long menuId, Integer quantity) {
        log.info("Selected LowestCostRestaurantSelection for menuId :"+menuId);
        Integer quantityBackup = quantity;
        List<RestaurantEntity> restaurantEntityList = new LinkedList<>();
        List<RestaurantMenuEntity> restaurantMenuEntityList = restaurantMenuRepository.findAllByMenuIdOrderByPrice(menuId);
        for(RestaurantMenuEntity restaurantMenuEntity : restaurantMenuEntityList) {
            if(restaurantMenuEntity.getRestaurantEntity().getIsActive()) {
                RestaurantCapacityEntity restaurantCapacityEntity = restaurantMenuEntity.getRestaurantEntity().getRestaurantCapacityEntity()
                        .stream()
                        .filter(restaurantCapacity -> restaurantCapacity.getState() == State.ACTIVE).toList().get(0);

                Integer maxCapacity = restaurantCapacityEntity.getMaxCapacity();
                Integer currentCapacity = restaurantCapacityEntity.getCurrentCapacity();
                if (maxCapacity.equals(currentCapacity)) {
                    continue;
                }
                log.info("Got Restaurant "+restaurantCapacityEntity.getRestaurantEntity().getName() + " id "+restaurantCapacityEntity.getRestaurantId() + " Max "+maxCapacity+ " current "+currentCapacity+" quantity "+quantityBackup);
                restaurantEntityList.add(restaurantMenuEntity.getRestaurantEntity());
                if(maxCapacity - currentCapacity >= quantityBackup) {
                    return restaurantEntityList;
                } else {
                    quantityBackup = quantityBackup - (maxCapacity - currentCapacity);
                }

            }
        }
        return null;
    }
}
