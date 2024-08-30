package com.intuit.foodorderingsystem.service.impl;


import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.entity.UsersEntity;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.exception.OrderCanNotBeCreatedException;
import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;

import com.intuit.foodorderingsystem.model.response.EmptyResponse;
import com.intuit.foodorderingsystem.repository.RestaurantCapacityRepository;
import com.intuit.foodorderingsystem.repository.RestaurantMenuRepository;
import com.intuit.foodorderingsystem.repository.UserRepository;
import com.intuit.foodorderingsystem.service.OrderService;
import com.intuit.foodorderingsystem.service.helper.RestaurantSelectionStrategy;
import com.intuit.foodorderingsystem.service.helper.RestaurantSelectionStrategyFactory;
import com.intuit.foodorderingsystem.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final RestaurantSelectionStrategyFactory restaurantSelectionStrategyFactory;
    private final RestaurantCapacityRepository restaurantCapacityRepository;

    private final TransactionUtil transactionUtil;

    @Override
    public EmptyResponse createOrder(Long userId, List<CreateOrderRequest> createOrderRequestList) {

        if (!userExist(userId)) {
            throw new DoNotExistException(Messages.USER_NOT_EXIST);
        }
        transactionUtil.processOrder(userId, createOrderRequestList);

        return null;
    }

    private Boolean userExist(Long userId) {
        Optional<UsersEntity> usersEntityOptional = userRepository.findById(userId);
        return usersEntityOptional.isPresent();
    }
}
