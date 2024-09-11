package com.intuit.foodorderingsystem.observer.impl;


import com.intuit.foodorderingsystem.observer.OrderNotificationObserver;
import com.intuit.foodorderingsystem.observer.OrderNotificationSubject;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class OrderNotificationSubjectImpl implements OrderNotificationSubject {

    private final List<OrderNotificationObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(OrderNotificationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderNotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Long orderId) {
        for (OrderNotificationObserver orderNotificationObserver : observers) {
            orderNotificationObserver.update(orderId);
        }
    }

}
