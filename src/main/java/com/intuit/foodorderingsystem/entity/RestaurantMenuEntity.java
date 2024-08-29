package com.intuit.foodorderingsystem.entity;

import com.intuit.foodorderingsystem.enums.ItemState;
import com.intuit.foodorderingsystem.enums.State;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "restaurant_menu")
@IdClass(RestaurantMenuCompositeKey.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuEntity {

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;

    @Id
    @Column(name = "restaurant_id")
    private Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false, insertable = false, updatable = false)
    private MenuEntity menuEntity;

    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "item_state", nullable = false)
    @Convert(converter = ItemState.Converter.class)
    private ItemState itemState;


}
