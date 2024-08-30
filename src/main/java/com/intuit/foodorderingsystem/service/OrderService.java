package com.intuit.foodorderingsystem.service;


import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.model.request.CreateUserRequest;
import com.intuit.foodorderingsystem.model.response.CreateUserResponse;
import com.intuit.foodorderingsystem.model.response.EmptyResponse;

import java.util.List;

public interface OrderService {

    EmptyResponse createOrder(Long userId, List<CreateOrderRequest> createOrderRequestList);


}
