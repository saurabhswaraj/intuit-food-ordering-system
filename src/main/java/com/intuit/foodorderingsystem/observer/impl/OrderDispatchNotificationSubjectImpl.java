package com.intuit.foodorderingsystem.observer.impl;


import com.intuit.foodorderingsystem.observer.OrderDispatchedNotificationObserver;
import com.intuit.foodorderingsystem.observer.OrderDispatchedNotificationSubject;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class OrderDispatchNotificationSubjectImpl implements OrderDispatchedNotificationSubject {
    private final List<OrderDispatchedNotificationObserver> observers = new ArrayList<>();

    @Override
    public void addObserver(OrderDispatchedNotificationObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(OrderDispatchedNotificationObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Long orderId) {
        for (OrderDispatchedNotificationObserver orderDispatchedNotificationObserver : observers) {
            orderDispatchedNotificationObserver.update(orderId);
        }
    }

}
