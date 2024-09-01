package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantCapacityRepository extends JpaRepository<RestaurantCapacityEntity, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value="5000")})
    List<RestaurantCapacityEntity> findByRestaurantIdIn(List<Long> restaurantId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value="5000")})
    RestaurantCapacityEntity findByRestaurantId(Long restaurantId);
}
