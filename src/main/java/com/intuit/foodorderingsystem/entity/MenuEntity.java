package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "menu")
@Getter
@Setter
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "menuEntity", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private List<RestaurantMenuEntity> restaurantMenuEntities;


}
