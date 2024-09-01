package com.intuit.foodorderingsystem.service.helper;

public interface OrderDispatchedNotificationSubject {
    void addObserver(OrderDispatchedNotificationObserver observer);
    void removeObserver(OrderDispatchedNotificationObserver observer);
    void notifyObservers(Long orderId);
}
