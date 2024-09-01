package com.intuit.foodorderingsystem.service.helper;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class OrderNotificationSubjectImpl implements OrderNotificationSubject {
    private List<OrderNotificationObserver> observers = new ArrayList<>();
    private Long orderId;

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
        this.orderId = orderId;
        for (OrderNotificationObserver orderNotificationObserver : observers) {
            orderNotificationObserver.update(orderId);
        }
    }

}
