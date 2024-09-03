package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantAuthenticationEntity;
import com.intuit.foodorderingsystem.entity.UserAuthenticationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantAuthenticationRepository extends JpaRepository<RestaurantAuthenticationEntity, Long> {


}
