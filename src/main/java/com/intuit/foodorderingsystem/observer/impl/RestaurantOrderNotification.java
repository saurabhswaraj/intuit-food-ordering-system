package com.intuit.foodorderingsystem.observer.impl;

import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.observer.OrderNotificationObserver;
import com.intuit.foodorderingsystem.observer.OrderNotificationSubject;
import com.intuit.foodorderingsystem.repository.OrdersRestaurantMenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RestaurantOrderNotification implements OrderNotificationObserver {

    private final OrdersRestaurantMenuRepository ordersRestaurantMenuRepository;
    private final OrderNotificationSubject orderNotificationSubject;

    @PostConstruct
    public void init () {
        log.info("Adding RestaurantOrderNotification to list");
        orderNotificationSubject.addObserver(this);
    }

    @Override
    public void update(Long orderId) {
        List<OrderRestaurantMenuEntity> orderRestaurantMenuEntityList = ordersRestaurantMenuRepository.findAllByOrderId(orderId);

        //Sending notification about orders which will send websocket notification / app notification to restaurant owners about new order

    }
}
