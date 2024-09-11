package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.UsersEntity;
import com.intuit.foodorderingsystem.model.response.CreateUserResponse;

public class CreateUserResponseBuilderFactory {

    public static CreateUserResponse build (UsersEntity usersEntity) {
        return CreateUserResponse.builder()
                .id(usersEntity.getId())
                .name(usersEntity.getName())
                .address(usersEntity.getAddress())
                .city(usersEntity.getCity())
                .state(usersEntity.getState())
                .isActive(usersEntity.getIsActive())
                .pinCode(usersEntity.getPinCode())
                .contactNumber(usersEntity.getContactNumber())
                .build();
    }
}
