package com.intuit.foodorderingsystem.service.impl;


import com.intuit.foodorderingsystem.builder.GetOrderDetailsResponseBuilderFactory;
import com.intuit.foodorderingsystem.builder.ItemOrderDetailsBuilderFactory;
import com.intuit.foodorderingsystem.builder.RestaurantOrderDetailsBuilderFactory;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.*;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.model.dto.ItemOrderDetails;
import com.intuit.foodorderingsystem.model.dto.RestaurantOrderDetails;
import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;

import com.intuit.foodorderingsystem.model.request.MarkDispatchOrderRequest;
import com.intuit.foodorderingsystem.model.response.CreateOrderResponse;
import com.intuit.foodorderingsystem.model.response.EmptyResponse;
import com.intuit.foodorderingsystem.model.response.GetorderDetailsResponse;
import com.intuit.foodorderingsystem.repository.*;
import com.intuit.foodorderingsystem.service.OrderService;
import com.intuit.foodorderingsystem.observer.OrderDispatchedNotificationSubject;
import com.intuit.foodorderingsystem.observer.OrderNotificationSubject;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrdersRestaurantMenuRepository ordersRestaurantMenuRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrdersRepository ordersRepository;
    private final OrderNotificationSubject orderNotificationSubject;
    private final OrderDispatchedNotificationSubject orderDispatchedNotificationSubject;
    private final OrderTransactionService orderTransactionService;

    @Override
    public CreateOrderResponse createOrder(Long userId, List<CreateOrderRequest> createOrderRequestList) {

        if (!userExist(userId)) {
            throw new DoNotExistException(Messages.USER_NOT_EXIST);
        }
        Long orderId = orderTransactionService.processOrder(userId, createOrderRequestList);

        orderNotificationSubject.notifyObservers(orderId);

        return CreateOrderResponse.builder().orderId(orderId).build();
    }

    @Override
    public EmptyResponse markOrderDispatched(MarkDispatchOrderRequest markDispatchOrderRequest) {
        if (!restaurantExist(markDispatchOrderRequest.getRestaurantId())) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST);
        }

        if (!orderExist(markDispatchOrderRequest.getOrderId())) {
            throw new DoNotExistException(Messages.ORDER_NOT_EXIST);
        }
        orderTransactionService.markOrderDispatched(markDispatchOrderRequest.getOrderId(), markDispatchOrderRequest.getRestaurantId());

        orderDispatchedNotificationSubject.notifyObservers(markDispatchOrderRequest.getOrderId());

        return new EmptyResponse();
    }

    @Override
    public GetorderDetailsResponse getOrderDetails(Long orderId) {

        if (!orderExist(orderId)) {
            throw new DoNotExistException(Messages.ORDER_NOT_EXIST);
        }

        Map<Long, List<ItemOrderDetails>> restaurantIdOrderDetailsMap = new HashMap<>();

        List<OrderRestaurantMenuEntity> orderRestaurantMenuEntityList = ordersRestaurantMenuRepository.findAllByOrderId(orderId);

        for(OrderRestaurantMenuEntity orderRestaurantMenuEntity : orderRestaurantMenuEntityList) {
            ItemOrderDetails itemOrderDetails = ItemOrderDetailsBuilderFactory.build(orderRestaurantMenuEntity);
            if (!restaurantIdOrderDetailsMap.containsKey(orderRestaurantMenuEntity.getRestaurantId())) {
                restaurantIdOrderDetailsMap.put(orderRestaurantMenuEntity.getRestaurantId(), new LinkedList<ItemOrderDetails>(List.of(itemOrderDetails)));
            } else {
                restaurantIdOrderDetailsMap.get(orderRestaurantMenuEntity.getRestaurantId()).add(itemOrderDetails);
            }
        }

        List<RestaurantOrderDetails> restaurantOrderDetailsList = new LinkedList<>();

        Float totalOrderPrice = (float) 0;
        for (Map.Entry<Long, List<ItemOrderDetails>> mapElement : restaurantIdOrderDetailsMap.entrySet()) {
            Long k = mapElement.getKey();
            List<ItemOrderDetails> v = mapElement.getValue();
            RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndIsActiveTrue(k);

            Float restaurantItemPrice = (float) 0;
            for(ItemOrderDetails itemOrderDetails : v) {
                restaurantItemPrice += itemOrderDetails.getTotalPrice();
            }
            totalOrderPrice += restaurantItemPrice;

            restaurantOrderDetailsList.add(RestaurantOrderDetailsBuilderFactory.build(restaurantEntity, v, restaurantItemPrice));
        }

        Optional<OrdersEntity> ordersEntityOptional = ordersRepository.findById(orderId);
        OrdersEntity ordersEntity = ordersEntityOptional.get();

        return GetOrderDetailsResponseBuilderFactory.build(ordersEntity,restaurantOrderDetailsList, totalOrderPrice);

    }


    private Boolean userExist(Long userId) {
        Optional<UsersEntity> usersEntityOptional = userRepository.findById(userId);
        return usersEntityOptional.isPresent();
    }

    private Boolean restaurantExist(Long restaurantId) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByIdAndIsActiveTrue(restaurantId);
        return restaurantEntity != null;
    }

    private Boolean orderExist(Long orderId) {
        Optional<OrdersEntity> ordersEntityOptional = ordersRepository.findById(orderId);
        return ordersEntityOptional.isPresent();
    }
}
