package com.intuit.foodorderingsystem.entity;

import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.enums.State;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Entity(name = "orders")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private UsersEntity usersEntity;

    @Column(name = "order_given_time", nullable = false)
    private ZonedDateTime orderGivenTime;

    @Column(name = "order_completion_time")
    private ZonedDateTime orderCompletionTime;

    @Column(name = "order_status", nullable = false)
    @Convert(converter = OrderStatus.Converter.class)
    private OrderStatus orderStatus;


}
