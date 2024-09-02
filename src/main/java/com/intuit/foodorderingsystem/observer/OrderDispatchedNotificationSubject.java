package com.intuit.foodorderingsystem.observer;

public interface OrderDispatchedNotificationSubject {
    void addObserver(OrderDispatchedNotificationObserver observer);
    void removeObserver(OrderDispatchedNotificationObserver observer);
    void notifyObservers(Long orderId);
}
