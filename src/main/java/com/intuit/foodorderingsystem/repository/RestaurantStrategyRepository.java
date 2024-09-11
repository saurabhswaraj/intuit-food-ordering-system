package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantStrategyEntity;
import com.intuit.foodorderingsystem.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RestaurantStrategyRepository extends JpaRepository<RestaurantStrategyEntity, Long> {

    Optional<RestaurantStrategyEntity> findByState(State state);

}
