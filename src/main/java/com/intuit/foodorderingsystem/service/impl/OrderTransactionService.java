package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.builder.OrderRestaurantMenuEntityBuilderFactory;
import com.intuit.foodorderingsystem.builder.OrdersEntityBuilderFactory;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.entity.OrdersEntity;
import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantMenuEntity;
import com.intuit.foodorderingsystem.enums.OrderStatus;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.exception.IneligibleRequestException;
import com.intuit.foodorderingsystem.exception.OrderCanNotBeCreatedException;
import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.repository.OrdersRepository;
import com.intuit.foodorderingsystem.repository.OrdersRestaurantMenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantCapacityRepository;
import com.intuit.foodorderingsystem.repository.RestaurantMenuRepository;
import com.intuit.foodorderingsystem.strategy.RestaurantSelectionStrategy;
import com.intuit.foodorderingsystem.strategy.factory.RestaurantSelectionStrategyFactory;
import jakarta.persistence.PessimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Log4j2
public class OrderTransactionService {

    private final RestaurantSelectionStrategyFactory restaurantSelectionStrategyFactory;

    private final OrdersRepository ordersRepository;

    private final OrdersRestaurantMenuRepository ordersRestaurantMenuRepository;

    private final RestaurantMenuRepository restaurantMenuRepository;

    private final RestaurantCapacityRepository restaurantCapacityRepository;

    @Value("${thread.config.retries}")
    private Integer retries;

    @Value("${thread.config.time-sleep-in-millis}")
    private Long sleepInMilliSec;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public long processOrder(Long userId, List<CreateOrderRequest> createOrderRequestList) throws InterruptedException {
        //create Order
        OrdersEntity ordersEntity = createOrder(userId);

        log.info("Starting order processing for order id "+ordersEntity.getId());

        //get Strategy
        RestaurantSelectionStrategy restaurantSelectionStrategy = restaurantSelectionStrategyFactory.getObject();
        for(CreateOrderRequest createOrderRequest : createOrderRequestList) {
            //get restaurant Map for each product
            Map<Long, Integer> restaurantIdQuantityMap = null;
            List<RestaurantCapacityEntity> restaurantCapacityEntities = null;

            int currentRetryCount = 0;
            while (currentRetryCount < retries) {
                restaurantIdQuantityMap = getRestaurantsFromStrategy(restaurantSelectionStrategy,createOrderRequest);
                try {
                    restaurantCapacityEntities = lockAndGetRestaurantCapacityEntities(restaurantIdQuantityMap);
                    break;
                } catch (PessimisticLockException pessimisticLockException) {
                    log.error(pessimisticLockException.getMessage());
                    currentRetryCount++;
                    log.warn("Lock found for restaurant list " + restaurantIdQuantityMap.keySet() + " trying again");
                    Thread.sleep(sleepInMilliSec);
                }
            }
            if(currentRetryCount >= retries) {
                throw new OrderCanNotBeCreatedException(Messages.ORDER_NOT_CREATED);
            }

            //Assigning Orders to Restaurants
            for (RestaurantCapacityEntity restaurantCapacityEntity : restaurantCapacityEntities) {

                Optional<RestaurantMenuEntity> restaurantMenuEntityOptional = restaurantMenuRepository.
                        findByRestaurantIdAndMenuId(restaurantCapacityEntity.getRestaurantId(), createOrderRequest.getMenuId());
                if (restaurantMenuEntityOptional.isPresent()) {
                    createRestaurantOrderMenu(ordersEntity, createOrderRequest, restaurantIdQuantityMap, restaurantCapacityEntity, restaurantMenuEntityOptional.get());

                }
                else{
                    throw new DoNotExistException("Restaurant Menu does not exist");
                }
            }
            log.info("Processed order "+ordersEntity.getId());
            restaurantCapacityRepository.saveAll(restaurantCapacityEntities);
        }
        return ordersEntity.getId();
    }

    private void createRestaurantOrderMenu(OrdersEntity ordersEntity, CreateOrderRequest createOrderRequest, Map<Long, Integer> restaurantEntityQuantityMap,
                                           RestaurantCapacityEntity restaurantCapacityEntity, RestaurantMenuEntity restaurantMenuEntity) {
        Integer quantity = restaurantEntityQuantityMap.get(restaurantCapacityEntity.getRestaurantId());
        restaurantCapacityEntity.setCurrentCapacity(
                restaurantCapacityEntity.getCurrentCapacity() + quantity);
        OrderRestaurantMenuEntity orderRestaurantMenuEntity = OrderRestaurantMenuEntityBuilderFactory
                    .build(ordersEntity.getId(), createOrderRequest, quantity,
                            restaurantCapacityEntity.getRestaurantId(), restaurantMenuEntity.getPrice());
        ordersRestaurantMenuRepository.save(orderRestaurantMenuEntity);
    }

    private OrdersEntity createOrder(Long userId) {
        OrdersEntity ordersEntity = OrdersEntityBuilderFactory.build(userId);
        ordersEntity = ordersRepository.save(ordersEntity);
        return ordersEntity;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void markOrderDispatched(Long orderId, Long restaurantId) throws InterruptedException {

        Integer capacity = 0;
        List<OrderRestaurantMenuEntity> orderRestaurantMenuEntityList = ordersRestaurantMenuRepository.findAllByOrderIdAndRestaurantId(orderId, restaurantId);

        if (orderRestaurantMenuEntityList.isEmpty()) {
            throw new IneligibleRequestException("The order does not belong to restaurant");
        }

        for(OrderRestaurantMenuEntity orderRestaurantMenuEntity : orderRestaurantMenuEntityList) {
            if(OrderStatus.DISPATCHED.equals(orderRestaurantMenuEntity.getOrderStatus())) {
                throw new IneligibleRequestException("The order is already dispatched");
            }
            orderRestaurantMenuEntity.setOrderCompletionTime(ZonedDateTime.now());
            orderRestaurantMenuEntity.setOrderStatus(OrderStatus.DISPATCHED);
            capacity += orderRestaurantMenuEntity.getQuantity();
        }
        ordersRestaurantMenuRepository.saveAll(orderRestaurantMenuEntityList);

        RestaurantCapacityEntity restaurantCapacityEntity = null;
        int currentRetryCount = 0;
        while (currentRetryCount < retries) {
            try {
                Optional<RestaurantCapacityEntity> restaurantCapacityEntityOptional = restaurantCapacityRepository.findByRestaurantId(restaurantId);
                if(restaurantCapacityEntityOptional.isPresent()) {
                    restaurantCapacityEntity = restaurantCapacityEntityOptional.get();
                    break;
                } else {
                    throw new DoNotExistException("Restaurant Capacity does not exist");
                }
            } catch (PessimisticLockException pessimisticLockException) {
                currentRetryCount++;
                log.warn("Lock found for restaurant id " + restaurantId + " trying again");
                Thread.sleep(sleepInMilliSec);
            }
        }
        if(currentRetryCount >= retries) {
            throw new OrderCanNotBeCreatedException(Messages.ORDER_NOT_CREATED);
        }
        restaurantCapacityEntity.setCurrentCapacity(restaurantCapacityEntity.getCurrentCapacity() - capacity);
        restaurantCapacityRepository.save(restaurantCapacityEntity);


    }

    private List<RestaurantCapacityEntity> lockAndGetRestaurantCapacityEntities(Map<Long, Integer> restaurantEntityQuantityMap){
         return restaurantCapacityRepository
                .findByRestaurantIdIn(restaurantEntityQuantityMap.keySet().stream().toList());

    }

    private Map<Long, Integer> getRestaurantsFromStrategy(RestaurantSelectionStrategy restaurantSelectionStrategy, CreateOrderRequest createOrderRequest) {
        Map<Long, Integer> restaurantEntityQuantityMap = restaurantSelectionStrategy.select(createOrderRequest.getMenuId(), createOrderRequest.getQuantity());
        if (restaurantEntityQuantityMap == null) {
            throw new OrderCanNotBeCreatedException(Messages.ORDER_NOT_CREATED);
        }
        return restaurantEntityQuantityMap;
    }
}
