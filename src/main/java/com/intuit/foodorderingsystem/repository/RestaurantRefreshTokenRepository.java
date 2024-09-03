package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantAuthenticationEntity;
import com.intuit.foodorderingsystem.entity.RestaurantRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRefreshTokenRepository extends JpaRepository<RestaurantRefreshTokenEntity, Long> {


}
