package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.entity.RestaurantMenuCompositeKey;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.ItemState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenuEntity, RestaurantMenuCompositeKey> {

    List<RestaurantMenuEntity> findAllByRestaurantId(Long restaurantId);

    List<RestaurantMenuEntity> findAllByRestaurantIdAndAndMenuIdIn(Long restaurantId, List<Long> menuIds);

    RestaurantMenuEntity findByRestaurantIdAndMenuId(Long restaurantId, Long menuId);

    List<RestaurantMenuEntity> findAllByMenuIdAnAndItemStateOrderByPrice(Long menuId, ItemState itemState);

}
