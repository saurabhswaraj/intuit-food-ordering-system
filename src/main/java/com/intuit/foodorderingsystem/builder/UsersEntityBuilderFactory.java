package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.UsersEntity;
import com.intuit.foodorderingsystem.model.request.CreateUserRequest;

public class UsersEntityBuilderFactory {

    public static UsersEntity build (CreateUserRequest createUserRequest) {
        return UsersEntity.builder()
                .name(createUserRequest.getName())
                .address(createUserRequest.getAddress())
                .city(createUserRequest.getCity())
                .state(createUserRequest.getState())
                .isActive(true)
                .pinCode(createUserRequest.getPinCode())
                .contactNumber(createUserRequest.getContactNumber())
                .build();
    }
}
