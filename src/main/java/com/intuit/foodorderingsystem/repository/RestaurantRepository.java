package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends PagingAndSortingRepository<RestaurantEntity, Long>, JpaRepository<RestaurantEntity, Long> {

    Optional<RestaurantEntity> findByContactNumber(String contactNumber);

    List<RestaurantEntity> findAllByIsActiveTrue(Pageable pageable);

    Optional<RestaurantEntity> findByIdAndIsActiveTrue(Long id);

    Optional<RestaurantEntity> findByContactNumberAndIsActiveTrue(String contactNumber);
}
