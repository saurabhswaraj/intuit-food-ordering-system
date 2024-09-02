package com.intuit.foodorderingsystem.entity;

import com.intuit.foodorderingsystem.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity(name = "order_restaurant_menu")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRestaurantMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false, insertable = false, updatable = false)
    private MenuEntity menuEntity;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false, insertable = false, updatable = false)
    private OrdersEntity ordersEntity;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false, insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="restaurant_id", referencedColumnName="restaurant_id", insertable = false, updatable = false),
            @JoinColumn(name="menu_id", referencedColumnName="menu_id", insertable = false, updatable = false)
    })
    private RestaurantMenuEntity restaurantMenuEntity;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "order_given_time", nullable = false)
    private ZonedDateTime orderGivenTime;

    @Column(name = "order_completion_time")
    private ZonedDateTime orderCompletionTime;

    @Column(name = "order_status", nullable = false)
    @Convert(converter = OrderStatus.Converter.class)
    private OrderStatus orderStatus;


}
