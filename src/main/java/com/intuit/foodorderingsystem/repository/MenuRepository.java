package com.intuit.foodorderingsystem.repository;

import com.intuit.foodorderingsystem.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    List<MenuEntity> findByNameIn(List<String> items);
}
