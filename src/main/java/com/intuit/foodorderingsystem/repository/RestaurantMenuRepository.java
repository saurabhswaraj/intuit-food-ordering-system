package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantMenuCompositeKey;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.ItemState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenuEntity, RestaurantMenuCompositeKey> {

    List<RestaurantMenuEntity> findAllByRestaurantIdAndMenuIdIn(Long restaurantId, List<Long> menuIds);

    Optional<RestaurantMenuEntity> findByRestaurantIdAndMenuId(Long restaurantId, Long menuId);

    List<RestaurantMenuEntity> findAllByMenuIdAndItemStateAndRestaurantEntity_IsActiveTrueOrderByPrice(Long menuId, ItemState itemState);

}
