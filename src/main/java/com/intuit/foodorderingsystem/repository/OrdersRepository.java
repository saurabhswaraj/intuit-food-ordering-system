package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, Long> {

}
