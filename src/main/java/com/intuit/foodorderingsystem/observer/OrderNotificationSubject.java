package com.intuit.foodorderingsystem.observer;

public interface OrderNotificationSubject {
    void addObserver(OrderNotificationObserver observer);
    void removeObserver(OrderNotificationObserver observer);
    void notifyObservers(Long orderId);
}
