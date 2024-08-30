package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersRestaurantMenuRepository extends JpaRepository<OrderRestaurantMenuEntity, Long> {
    
}
