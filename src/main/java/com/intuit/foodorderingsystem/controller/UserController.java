package com.intuit.foodorderingsystem.controller;


import com.intuit.foodorderingsystem.model.request.CreateUserRequest;
import com.intuit.foodorderingsystem.model.response.BaseResponseModel;
import com.intuit.foodorderingsystem.model.response.CreateUserResponse;
import com.intuit.foodorderingsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/user")
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    BaseResponseModel<CreateUserResponse> createUser (@Valid @RequestBody CreateUserRequest createUserRequest) {
        return new BaseResponseModel<>(userService.createUser(createUserRequest));
    }


}
