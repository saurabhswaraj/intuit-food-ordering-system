package com.intuit.foodorderingsystem.observer.impl;

import com.intuit.foodorderingsystem.observer.OrderNotificationObserver;
import com.intuit.foodorderingsystem.observer.OrderNotificationSubject;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class RiderOrderNotification implements OrderNotificationObserver {

    private final OrderNotificationSubject orderNotificationSubject;

    @PostConstruct
    public void init () {
        log.info("Adding RiderOrderNotification to list");
        orderNotificationSubject.addObserver(this);
    }

    @Override
    public void update(Long orderId) {
        log.info("Sending Riders a notification about order");
        log.info("OrderId :"+orderId);
        //Sending notification about orders which will send websocket notification / app notification to riders about new order
    }
}
