package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrdersRestaurantMenuRepository extends JpaRepository<OrderRestaurantMenuEntity, Long> {

    List<OrderRestaurantMenuEntity> findAllByOrderId(Long orderId);

    List<OrderRestaurantMenuEntity> findAllByOrderIdAndRestaurantId(Long orderId, Long restaurantId);
    
}
