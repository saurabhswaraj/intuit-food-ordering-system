package com.intuit.foodorderingsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity(name = "restaurant_menu")
@IdClass(RestaurantMenuCompositeKey.class)
@Getter
@Setter
public class RestaurantMenuEntity {

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantEntity restaurantEntity;

    @Id
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private MenuEntity menuEntity;

    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "rating")
    private String rating;


}
