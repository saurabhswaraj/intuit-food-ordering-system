package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.builder.*;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.constant.RegexConstants;
import com.intuit.foodorderingsystem.entity.RestaurantCapacityEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.enums.State;
import com.intuit.foodorderingsystem.exception.AlreadyExistException;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.exception.PhoneNumberNotValidException;
import com.intuit.foodorderingsystem.model.request.CreateRestaurantRequest;
import com.intuit.foodorderingsystem.model.request.EditRestaurantRequest;
import com.intuit.foodorderingsystem.model.response.CreateRestaurantResponse;
import com.intuit.foodorderingsystem.model.response.EditRestaurantResponse;
import com.intuit.foodorderingsystem.model.response.EmptyResponse;
import com.intuit.foodorderingsystem.model.response.GetRestaurantResponse;
import com.intuit.foodorderingsystem.repository.RestaurantCapacityRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import com.intuit.foodorderingsystem.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantCapacityRepository restaurantCapacityRepository;
    private final RestaurantTransactionService restaurantTransactionService;

    @Override
    public CreateRestaurantResponse createRestaurant(CreateRestaurantRequest createRestaurantRequest) {

        if(restaurantExist(createRestaurantRequest.getContactNumber())) {
            throw new AlreadyExistException(Messages.RESTAURANT_ALREADY_EXIST);
        }
        RestaurantEntity restaurantEntity = RestaurantEntityBuilderFactory.build(createRestaurantRequest);
        RestaurantCapacityEntity restaurantCapacityEntity = restaurantTransactionService.saveRestaurantDetails(restaurantEntity, createRestaurantRequest);
        return CreateRestaurantResponseBuilderFactory.build(restaurantEntity, restaurantCapacityEntity);
    }

    @Override
    public List<GetRestaurantResponse> getAllRestaurants() {
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAllByIsActiveTrue();
        return restaurantEntities.stream()
                .map(restaurantEntity -> GetRestaurantResponseBuilderFactory.build(restaurantEntity,
                        restaurantEntity.getRestaurantCapacityEntity().stream()
                                .filter(restaurantCapacityEntity -> restaurantCapacityEntity.getState() == State.ACTIVE)
                                .toList().get(0))).toList();
    }

    @Override
    public GetRestaurantResponse getSingleRestaurant(Long restaurantId) {
        RestaurantEntity restaurantEntity = getRestaurantEntity(restaurantId);
        if(restaurantEntity == null) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST);
        }
        return GetRestaurantResponseBuilderFactory.build(restaurantEntity,
                        restaurantEntity.getRestaurantCapacityEntity().stream()
                                .filter(restaurantCapacityEntity -> restaurantCapacityEntity.getState() == State.ACTIVE)
                                .toList().get(0));
    }

    @Override
    public EditRestaurantResponse editRestaurants(Long restaurantId, EditRestaurantRequest editRestaurantRequest) {
        RestaurantEntity restaurantEntity = getRestaurantEntity(restaurantId);
        if(restaurantEntity == null) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST);
        }
        if (!StringUtils.isAllBlank(editRestaurantRequest.getName())) {
            restaurantEntity.setName(editRestaurantRequest.getName());
        }
        if (!StringUtils.isAllBlank(editRestaurantRequest.getAddress())) {
            restaurantEntity.setAddress(editRestaurantRequest.getAddress());
        }
        if (!StringUtils.isAllBlank(editRestaurantRequest.getCity())) {
            restaurantEntity.setCity(editRestaurantRequest.getCity());
        }
        if (!StringUtils.isAllBlank(editRestaurantRequest.getState())) {
            restaurantEntity.setState(editRestaurantRequest.getState());
        }
        if (!StringUtils.isAllBlank(editRestaurantRequest.getPinCode())) {
            restaurantEntity.setPinCode(editRestaurantRequest.getPinCode());
        }
        if (!StringUtils.isAllBlank(editRestaurantRequest.getContactNumber())) {
            if(!editRestaurantRequest.getContactNumber().matches(RegexConstants.PHONE_NUMBER_REGEX)) {
                throw new PhoneNumberNotValidException(Messages.PHONE_NUMBER_CHECK);
            }
            restaurantEntity.setContactNumber(editRestaurantRequest.getContactNumber());
        }
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        return EditRestaurantResponseBuilderFactory.build(restaurantEntity);
    }

    @Override
    public EmptyResponse deactivateRestaurants(Long restaurantId) {
        RestaurantEntity restaurantEntity = getRestaurantEntity(restaurantId);
        if(restaurantEntity == null) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST);
        }
        restaurantEntity.setIsActive(false);
        restaurantRepository.save(restaurantEntity);
        return new EmptyResponse();
    }

    private Boolean restaurantExist(String contactNumber) {
        RestaurantEntity restaurantEntity = restaurantRepository.findByContactNumber(contactNumber);
        return restaurantEntity != null;
    }

    private RestaurantEntity getRestaurantEntity(Long restaurantId) {
        return restaurantRepository.findByIdAndIsActiveTrue(restaurantId);
    }


}
