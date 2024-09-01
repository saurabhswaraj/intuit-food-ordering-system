package com.intuit.foodorderingsystem.service.helper;

public interface OrderNotificationSubject {
    void addObserver(OrderNotificationObserver observer);
    void removeObserver(OrderNotificationObserver observer);
    void notifyObservers(Long orderId);
}
