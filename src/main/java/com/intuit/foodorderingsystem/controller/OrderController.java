package com.intuit.foodorderingsystem.controller;


import com.intuit.foodorderingsystem.model.request.CreateOrderRequest;
import com.intuit.foodorderingsystem.model.request.CreateUserRequest;
import com.intuit.foodorderingsystem.model.request.DeleteItemsMenuRequest;
import com.intuit.foodorderingsystem.model.response.BaseResponseModel;
import com.intuit.foodorderingsystem.model.response.CreateUserResponse;
import com.intuit.foodorderingsystem.model.response.EmptyResponse;
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
    BaseResponseModel<EmptyResponse> createOrder (@PathVariable Long userId, @RequestBody List<CreateOrderRequest> createOrderRequestList) {
        return new BaseResponseModel<>(orderService.createOrder(userId, createOrderRequestList));
    }


}
