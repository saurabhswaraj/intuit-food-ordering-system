package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.OrdersEntity;
import com.intuit.foodorderingsystem.entity.RestaurantStrategyEntity;
import com.intuit.foodorderingsystem.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

}
