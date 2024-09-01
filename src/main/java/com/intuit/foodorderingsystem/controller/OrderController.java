package com.intuit.foodorderingsystem.controller;


import com.intuit.foodorderingsystem.model.request.*;
import com.intuit.foodorderingsystem.model.response.*;
import com.intuit.foodorderingsystem.service.OrderService;
import com.intuit.foodorderingsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Controller
@RequestMapping("/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping({"{userId}"})
    BaseResponseModel<CreateOrderResponse> createOrder (@PathVariable Long userId, @RequestBody List<CreateOrderRequest> createOrderRequestList) {
        return new BaseResponseModel<>(orderService.createOrder(userId, createOrderRequestList));
    }

    @PutMapping("/mark-dispatched")
    BaseResponseModel<EmptyResponse> markOrderDispatched (@RequestBody MarkDispatchOrderRequest markDispatchOrderRequest) {
        return new BaseResponseModel<>(orderService.markOrderDispatched(markDispatchOrderRequest));
    }

    @GetMapping("/order-details")
    BaseResponseModel<GetorderDetailsResponse> getOrderDetails(@RequestParam("orderId") Long orderId) {
        return new BaseResponseModel<>(orderService.getOrderDetails(orderId));
    }


}
