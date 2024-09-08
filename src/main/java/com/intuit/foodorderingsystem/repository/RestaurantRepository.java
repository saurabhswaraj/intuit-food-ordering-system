package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<RestaurantEntity, Long>, JpaRepository<RestaurantEntity, Long> {
    RestaurantEntity findByContactNumber(String contactNumber);
    List<RestaurantEntity> findAllByIsActiveTrue(Pageable pageable);
    RestaurantEntity findByIdAndIsActiveTrue(Long id);
}
