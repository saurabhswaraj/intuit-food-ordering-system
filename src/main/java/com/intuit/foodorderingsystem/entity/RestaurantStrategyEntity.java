package com.intuit.foodorderingsystem.entity;

import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity(name = "restaurant_strategy")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantStrategyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "strategy_qualifier_name", nullable = false)
    private String strategyQualifierName;

    @Column(name = "state", nullable = false)
    @Convert(converter = State.Converter.class)
    private State state;


}
