package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    UsersEntity findByContactNumber(String contactNumber);

}
