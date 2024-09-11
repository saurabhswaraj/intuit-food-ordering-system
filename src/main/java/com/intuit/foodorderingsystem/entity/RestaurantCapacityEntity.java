package com.intuit.foodorderingsystem.entity;

import com.intuit.foodorderingsystem.enums.State;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "restaurant_capacity")
public class RestaurantCapacityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", updatable = false, insertable = false)
    private RestaurantEntity restaurantEntity;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "current_capacity", nullable = false)
    private Integer currentCapacity;

    @Column(name = "state", nullable = false)
    @Convert(converter = State.Converter.class)
    private State state;


}
