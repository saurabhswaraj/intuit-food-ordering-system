package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantCapacityRepository extends JpaRepository<RestaurantCapacityEntity, Long> {
}
