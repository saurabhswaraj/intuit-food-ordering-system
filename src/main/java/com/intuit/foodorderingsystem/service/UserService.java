package com.intuit.foodorderingsystem.service;


import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.request.CreateUserRequest;
import com.intuit.foodorderingsystem.model.request.EditRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.*;

import java.util.List;

public interface UserService {

    CreateUserResponse createUser(CreateUserRequest createUserRequest);


}
