package com.intuit.foodorderingsystem.entity;

import com.intuit.foodorderingsystem.enums.FoodType;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "menu")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Convert(converter = FoodType.Converter.class)
    @Column(name = "food_type", nullable = false)
    private FoodType foodType;

    @OneToMany(mappedBy = "menuEntity", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
    private List<RestaurantMenuEntity> restaurantMenuEntities;


}
