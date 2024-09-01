package com.intuit.foodorderingsystem.service;


import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.model.request.MarkDispatchOrderRequest;
import com.intuit.foodorderingsystem.model.response.CreateOrderResponse;
import com.intuit.foodorderingsystem.model.response.EmptyResponse;
import com.intuit.foodorderingsystem.model.response.GetorderDetailsResponse;

import java.util.List;

public interface OrderService {

    CreateOrderResponse createOrder(Long userId, List<CreateOrderRequest> createOrderRequestList);

    EmptyResponse markOrderDispatched(MarkDispatchOrderRequest markDispatchOrderRequest);

    GetorderDetailsResponse getOrderDetails(Long orderId);


}
