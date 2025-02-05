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
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public List<GetRestaurantResponse> getAllRestaurants(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        List<RestaurantEntity> restaurantEntities = restaurantRepository.findAllByIsActiveTrue(pageRequest);
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
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST_OR_DISABLED);
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
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST_OR_DISABLED);
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
            if (restaurantRepository.findByContactNumberAndIsActiveTrue(editRestaurantRequest.getContactNumber()).isPresent()
             && !editRestaurantRequest.getContactNumber().equals(restaurantEntity.getContactNumber())) {
                throw new AlreadyExistException("This contact number is already registered");
            }
            restaurantEntity.setContactNumber(editRestaurantRequest.getContactNumber());
        }
        Integer restaurantCapacity = restaurantEntity.getRestaurantCapacityEntity().stream()
                .filter(restaurantCapacityEntity -> restaurantCapacityEntity.getState() == State.ACTIVE).toList().get(0).getMaxCapacity();
        restaurantEntity = restaurantRepository.save(restaurantEntity);
        return EditRestaurantResponseBuilderFactory.build(restaurantEntity, restaurantCapacity);
    }

    @Override
    public EmptyResponse deactivateRestaurants(Long restaurantId) {
        RestaurantEntity restaurantEntity = getRestaurantEntity(restaurantId);
        if(restaurantEntity == null) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST_OR_DISABLED);
        }
        restaurantEntity.setIsActive(false);
        restaurantRepository.save(restaurantEntity);
        return new EmptyResponse();
    }

    @Override
    public EmptyResponse activateRestaurants(Long restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findById(restaurantId);
        if(restaurantEntityOptional.isEmpty()) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST);
        }
        RestaurantEntity restaurantEntity = restaurantEntityOptional.get();
        restaurantEntity.setIsActive(true);
        restaurantRepository.save(restaurantEntity);
        return new EmptyResponse();
    }

    private Boolean restaurantExist(String contactNumber) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findByContactNumber(contactNumber);
        return restaurantEntityOptional.isPresent();
    }

    private RestaurantEntity getRestaurantEntity(Long restaurantId) {
        Optional<RestaurantEntity> restaurantEntityOptional = restaurantRepository.findByIdAndIsActiveTrue(restaurantId);
        if(restaurantEntityOptional.isEmpty() ) {
            throw new DoNotExistException(Messages.RESTAURANT_NOT_EXIST_OR_DISABLED);
        }
        return restaurantEntityOptional.get();
    }


}
