package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.UserAuthenticationEntity;
import com.intuit.foodorderingsystem.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationRepository extends JpaRepository<UserAuthenticationEntity, Long> {


}
