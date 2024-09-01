package com.intuit.foodorderingsystem.service.helper;


import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.entity.OrdersEntity;
import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.repository.OrdersRepository;
import com.intuit.foodorderingsystem.repository.OrdersRestaurantMenuRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderCompletion implements OrderDispatchedNotificationObserver{

    private final OrdersRestaurantMenuRepository ordersRestaurantMenuRepository;
    private final OrderDispatchedNotificationSubject orderDispatchedNotificationSubject;
    private final OrdersRepository ordersRepository;

    @PostConstruct
    public void init () {
        log.info("Adding RestaurantOrderNotification to list");
        orderDispatchedNotificationSubject.addObserver(this);
    }

    @Override
    public void update(Long orderId) {
        List<OrderRestaurantMenuEntity> orderRestaurantMenuEntityList = ordersRestaurantMenuRepository.findAllByOrderId(orderId);
        for(OrderRestaurantMenuEntity orderRestaurantMenuEntity : orderRestaurantMenuEntityList) {
            if(orderRestaurantMenuEntity.getOrderStatus() != OrderStatus.DISPATCHED ) {
                return;
            }
        }
        Optional<OrdersEntity> ordersEntityOptional = ordersRepository.findById(orderId);
        OrdersEntity ordersEntity = ordersEntityOptional.get();
        ordersEntity.setOrderCompletionTime(ZonedDateTime.now());
        ordersEntity.setOrderStatus(OrderStatus.DISPATCHED);
        ordersRepository.save(ordersEntity);
    }
}
