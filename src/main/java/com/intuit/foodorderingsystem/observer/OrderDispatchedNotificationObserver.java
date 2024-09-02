package com.intuit.foodorderingsystem.observer;

public interface OrderDispatchedNotificationObserver {
    void update(Long orderId);
}
