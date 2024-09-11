package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByContactNumber(String contactNumber);

}
