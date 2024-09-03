package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.builder.CreateUserResponseBuilderFactory;
import com.intuit.foodorderingsystem.builder.UsersEntityBuilderFactory;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.entity.UsersEntity;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.model.request.CreateUserRequest;
import com.intuit.foodorderingsystem.model.response.CreateUserResponse;
import com.intuit.foodorderingsystem.repository.UserRepository;
import com.intuit.foodorderingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserTransactionService userTransactionService;

    @Override
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if(userExist(createUserRequest.getContactNumber())) {
            throw new DoNotExistException(Messages.USER_ALREADY_EXIST);
        }
        UsersEntity usersEntity = userTransactionService.createUser(createUserRequest);

        return CreateUserResponseBuilderFactory.build(usersEntity);
    }

    private Boolean userExist(String contactNumber) {
        UsersEntity usersEntity = userRepository.findByContactNumber(contactNumber);
        return usersEntity != null;
    }


}
