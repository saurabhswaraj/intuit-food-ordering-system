package com.intuit.foodorderingsystem.service.helper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
public class RiderOrderNotification implements OrderNotificationObserver{

    private final OrderNotificationSubject orderNotificationSubject;

    @PostConstruct
    public void init () {
        log.info("Adding RiderOrderNotification to list");
        orderNotificationSubject.addObserver(this);
    }

    @Override
    public void update(Long orderId) {
        log.info("Sending Rider Service a notification about order");
        log.info("OrderId :"+orderId);
    }
}
