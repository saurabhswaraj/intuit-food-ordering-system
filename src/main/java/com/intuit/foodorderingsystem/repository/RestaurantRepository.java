package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    RestaurantEntity findByContactNumber(String contactNumber);
    List<RestaurantEntity> findAllByIsActiveTrue();
    RestaurantEntity findByIdAndIsActiveTrue(Long id);
}
