package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.builder.OrderRestaurantMenuEntityBuilderFactory;
import com.intuit.foodorderingsystem.builder.OrdersEntityBuilderFactory;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.*;
import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.exception.OrderCanNotBeCreatedException;
import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.repository.OrdersRepository;
import com.intuit.foodorderingsystem.repository.OrdersRestaurantMenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantCapacityRepository;
import com.intuit.foodorderingsystem.repository.RestaurantMenuRepository;
import com.intuit.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import com.intuit.foodorderingsystem.strategy.factory.RestaurantSelectionStrategyFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Log4j2
public class OrderTransactionService {

    private final RestaurantSelectionStrategyFactory restaurantSelectionStrategyFactory;

    private final OrdersRepository ordersRepository;

    private final OrdersRestaurantMenuRepository ordersRestaurantMenuRepository;

    private final RestaurantMenuRepository restaurantMenuRepository;

    private final RestaurantCapacityRepository restaurantCapacityRepository;

    @Transactional
    public long processOrder(Long userId, List<CreateOrderRequest> createOrderRequestList) {

        OrdersEntity ordersEntity = OrdersEntityBuilderFactory.build(userId);
        ordersEntity = ordersRepository.save(ordersEntity);

        RestaurantSelectionStrategy restaurantSelectionStrategy = restaurantSelectionStrategyFactory.getObject();
        for(CreateOrderRequest createOrderRequest : createOrderRequestList) {
            List<RestaurantEntity> restaurantEntities = restaurantSelectionStrategy.select(createOrderRequest.getMenuId(), createOrderRequest.getQuantity());
            if(restaurantEntities == null) {
                throw new OrderCanNotBeCreatedException(Messages.ORDER_NOT_CREATED);
            }

            //after (SELECT IN) query the order will be different than restaurantEntities but we need same order
            List<RestaurantCapacityEntity> restaurantCapacityEntities = restaurantCapacityRepository
                    .findByRestaurantIdIn(restaurantEntities.stream().map(RestaurantEntity::getId).toList());

            List<RestaurantCapacityEntity> orderedRestaurantCapacityEntities = new LinkedList<>();
            for(RestaurantEntity restaurantEntity : restaurantEntities) {
                log.info("Selected " + restaurantEntity.getName() + " no " + restaurantEntity.getId());
                for(RestaurantCapacityEntity restaurantCapacityEntity : restaurantCapacityEntities) {
                    if(restaurantEntity.getId().equals(restaurantCapacityEntity.getRestaurantId())) {
                        orderedRestaurantCapacityEntities.add(restaurantCapacityEntity);
                        break;
                    }
                }
            }

            Integer quantity = createOrderRequest.getQuantity();
            for(RestaurantCapacityEntity restaurantCapacityEntity : orderedRestaurantCapacityEntities) {

                RestaurantMenuEntity restaurantMenuEntity = restaurantMenuRepository.findByRestaurantIdAndMenuId(restaurantCapacityEntity.getRestaurantId(), createOrderRequest.getMenuId());
                Integer maxCapacity = restaurantCapacityEntity.getMaxCapacity();
                Integer curCapacity = restaurantCapacityEntity.getCurrentCapacity();
                if(quantity <= maxCapacity - curCapacity) {
                    restaurantCapacityEntity.setCurrentCapacity(curCapacity + quantity);
                    OrderRestaurantMenuEntity orderRestaurantMenuEntity = OrderRestaurantMenuEntityBuilderFactory
                            .build(ordersEntity.getId(), createOrderRequest, quantity, restaurantCapacityEntity.getRestaurantId(), restaurantMenuEntity.getPrice());
                    ordersRestaurantMenuRepository.save(orderRestaurantMenuEntity);
                    quantity = 0;
                } else {
                    restaurantCapacityEntity.setCurrentCapacity(maxCapacity);
                    OrderRestaurantMenuEntity orderRestaurantMenuEntity = OrderRestaurantMenuEntityBuilderFactory
                            .build(ordersEntity.getId(), createOrderRequest, (maxCapacity - curCapacity), restaurantCapacityEntity.getRestaurantId(), restaurantMenuEntity.getPrice());
                    ordersRestaurantMenuRepository.save(orderRestaurantMenuEntity);
                    quantity = quantity - (maxCapacity - curCapacity);
                }
            }
            restaurantCapacityRepository.saveAll(restaurantCapacityEntities);
            log.info("ho gya");
        }

        return ordersEntity.getId();
    }

    @Transactional
    public void markOrderDispatched(Long orderId, Long restaurantId) {

        Integer capacity = 0;
        List<OrderRestaurantMenuEntity> orderRestaurantMenuEntityList = ordersRestaurantMenuRepository.findAllByOrderIdAndRestaurantId(orderId, restaurantId);
        for(OrderRestaurantMenuEntity orderRestaurantMenuEntity : orderRestaurantMenuEntityList) {
            orderRestaurantMenuEntity.setOrderCompletionTime(ZonedDateTime.now());
            orderRestaurantMenuEntity.setOrderStatus(OrderStatus.DISPATCHED);
            capacity += orderRestaurantMenuEntity.getQuantity();
        }
        ordersRestaurantMenuRepository.saveAll(orderRestaurantMenuEntityList);

        RestaurantCapacityEntity restaurantCapacityEntity = restaurantCapacityRepository.findByRestaurantId(restaurantId);
        restaurantCapacityEntity.setCurrentCapacity(restaurantCapacityEntity.getCurrentCapacity() - capacity);
        restaurantCapacityRepository.save(restaurantCapacityEntity);

    }
}
