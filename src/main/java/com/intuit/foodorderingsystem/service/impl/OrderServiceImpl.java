package com.intuit.foodorderingsystem.service.impl;


import com.intuit.foodorderingsystem.builder.GetOrderDetailsResponseBuilderFactory;
import com.intuit.foodorderingsystem.builder.ItemOrderDetailsBuilderFactory;
import com.intuit.foodorderingsystem.builder.RestaurantOrderDetailsBuilderFactory;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.OrderRestaurantMenuEntity;
import com.intuit.foodorderingsystem.entity.OrdersEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.entity.UsersEntity;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.model.request.MarkDispatchOrderRequest;
import com.intuit.foodorderingsystem.model.response.*;
import com.intuit.foodorderingsystem.observer.OrderDispatchedNotificationSubject;
import com.intuit.foodorderingsystem.observer.OrderNotificationSubject;
import com.intuit.foodorderingsystem.repository.OrdersRepository;
import com.intuit.foodorderingsystem.repository.OrdersRestaurantMenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import com.intuit.foodorderingsystem.repository.UserRepository;
import com.intuit.foodorderingsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
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

    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    public CreateOrderResponse createOrder(Long userId, List<CreateOrderRequest> createOrderRequestList) throws InterruptedException {

        checkIfUserExists(userId);
        Long orderId = orderTransactionService.processOrder(userId, createOrderRequestList);

        orderNotificationSubject.notifyObservers(orderId);

        return CreateOrderResponse.builder().orderId(orderId).build();
    }

    @Override
    public EmptyResponse markOrderDispatched(MarkDispatchOrderRequest markDispatchOrderRequest) throws InterruptedException {
        getActiveRestaurantById(markDispatchOrderRequest.getRestaurantId());

        getIfOrderExists(markDispatchOrderRequest.getOrderId());

        orderTransactionService.markOrderDispatched(markDispatchOrderRequest.getOrderId(), markDispatchOrderRequest.getRestaurantId());

        orderDispatchedNotificationSubject.notifyObservers(markDispatchOrderRequest.getOrderId());

        return new EmptyResponse();
    }

    @Override
    public GetorderDetailsResponse getOrderDetails(Long orderId) {

        OrdersEntity ordersEntity = getIfOrderExists(orderId);
        Map<Long, List<ItemOrderDetails>> restaurantIdOrderDetailsMap = new HashMap<>();

        List<OrderRestaurantMenuEntity> orderRestaurantMenuEntityList = ordersRestaurantMenuRepository.findAllByOrderId(orderId);

        for(OrderRestaurantMenuEntity orderRestaurantMenuEntity : orderRestaurantMenuEntityList) {
            ItemOrderDetails itemOrderDetails = ItemOrderDetailsBuilderFactory.build(orderRestaurantMenuEntity);
            if (!restaurantIdOrderDetailsMap.containsKey(orderRestaurantMenuEntity.getRestaurantId())) {
                restaurantIdOrderDetailsMap.put(orderRestaurantMenuEntity.getRestaurantId(), new LinkedList<>(List.of(itemOrderDetails)));
            } else {
                restaurantIdOrderDetailsMap.get(orderRestaurantMenuEntity.getRestaurantId()).add(itemOrderDetails);
            }
        }

        List<RestaurantOrderDetails> restaurantOrderDetailsList = new LinkedList<>();

        float totalOrderPrice = (float) 0;
        for (Map.Entry<Long, List<ItemOrderDetails>> mapElement : restaurantIdOrderDetailsMap.entrySet()) {
            Long restuarantId = mapElement.getKey();
            List<ItemOrderDetails> orderDetails = mapElement.getValue();
            RestaurantEntity restaurantEntity = getActiveRestaurantById(restuarantId);

            float restaurantItemPrice = (float) 0;
            for(ItemOrderDetails itemOrderDetails : orderDetails) {
                restaurantItemPrice += itemOrderDetails.getTotalPrice();
            }
            totalOrderPrice += restaurantItemPrice;

            restaurantOrderDetailsList.add(RestaurantOrderDetailsBuilderFactory.build(restaurantEntity, orderDetails, Float.valueOf(decimalFormat.format(restaurantItemPrice))));
        }

        return GetOrderDetailsResponseBuilderFactory.build(ordersEntity,restaurantOrderDetailsList, Float.valueOf(decimalFormat.format(totalOrderPrice)));

    }


    private void checkIfUserExists(Long userId) {
        Optional<UsersEntity> usersEntityOptional = userRepository.findById(userId);
        if (usersEntityOptional.isEmpty()) {
            throw new DoNotExistException(Messages.USER_NOT_EXIST);
        }
    }


    private RestaurantEntity getActiveRestaurantById(Long restaurantId){
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findByIdAndIsActiveTrue(restaurantId);
        if(restaurantEntityOptional.isEmpty() ) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST_OR_DISABLED);
        }
        return restaurantEntityOptional.get();
    }

    private OrdersEntity getIfOrderExists(Long orderId) {
        Optional<OrdersEntity> ordersEntityOptional = ordersRepository.findById(orderId);
        if (ordersEntityOptional.isEmpty()) {
            throw new DoNotExistException(Messages.ORDER_NOT_EXIST);
        }
        return ordersEntityOptional.get();
    }
}
