package com.intuit.foodorderingsystem.controller;


import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.model.request.MarkDispatchOrderRequest;
import com.intuit.foodorderingsystem.model.response.BaseResponseModel;
import com.intuit.foodorderingsystem.model.response.CreateOrderResponse;
import com.intuit.foodorderingsystem.model.response.EmptyResponse;
import com.intuit.foodorderingsystem.model.response.GetorderDetailsResponse;
import com.intuit.foodorderingsystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping({"{userId}"})
    BaseResponseModel<CreateOrderResponse> createOrder (@PathVariable Long userId, @RequestBody List<CreateOrderRequest> createOrderRequestList) throws InterruptedException {
        return new BaseResponseModel<>(orderService.createOrder(userId, createOrderRequestList));
    }

    @PutMapping("/mark-dispatched")
    BaseResponseModel<EmptyResponse> markOrderDispatched (@RequestBody MarkDispatchOrderRequest markDispatchOrderRequest) throws InterruptedException {
        return new BaseResponseModel<>(orderService.markOrderDispatched(markDispatchOrderRequest));
    }

    @GetMapping("/order-details")
    BaseResponseModel<GetorderDetailsResponse> getOrderDetails(@RequestParam("orderId") Long orderId) {
        return new BaseResponseModel<>(orderService.getOrderDetails(orderId));
    }


}
