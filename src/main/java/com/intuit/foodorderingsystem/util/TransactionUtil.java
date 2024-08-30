package com.intuit.foodorderingsystem.util;

import com.intuit.foodorderingsystem.builder.OrderRestaurantMenuEntityBuilderFactory;
import com.intuit.foodorderingsystem.builder.OrdersEntityBuilderFactory;
import com.intuit.foodorderingsystem.builder.RestaurantCapacityEntityBuilderFactory;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.*;
import com.intuit.foodorderingsystem.exception.OrderCanNotBeCreatedException;
import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.repository.*;
import com.intuit.foodorderingsystem.service.helper.RestaurantSelectionStrategy;
import com.intuit.foodorderingsystem.service.helper.RestaurantSelectionStrategyFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class TransactionUtil {

    private final RestaurantRepository restaurantRepository;

    private final RestaurantCapacityRepository restaurantCapacityRepository;

    private final RestaurantSelectionStrategyFactory restaurantSelectionStrategyFactory;

    private final OrdersRepository ordersRepository;

    private final OrdersRestaurantMenuRepository ordersRestaurantMenuRepository;

    private final RestaurantMenuRepository restaurantMenuRepository;


    @Transactional
    public RestaurantCapacityEntity saveRestaurantDetails(RestaurantEntity restaurantEntity, CreateRestaurantRequest createRestaurantRequest) {
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        log.info(restaurantEntity);
        RestaurantCapacityEntity restaurantCapacityEntity = RestaurantCapacityEntityBuilderFactory.build(
                createRestaurantRequest.getMaxOrderCapacity(), restaurantEntity.getId());
        restaurantCapacityEntity = restaurantCapacityRepository.save(restaurantCapacityEntity);
        return restaurantCapacityEntity;
    }

    @Transactional
    public void processOrder(Long userId, List<CreateOrderRequest> createOrderRequestList) {

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
    }
}
