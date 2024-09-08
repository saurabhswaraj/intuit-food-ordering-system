package com.intuit.foodorderingsystem.service.impl;

import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.entity.RestaurantEntity;
import com.intuit.foodorderingsystem.enums.FoodType;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.exception.IneligibleRequestException;
import com.intuit.foodorderingsystem.model.dto.CreateItem;
import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.response.CreateMenuResponse;
import com.intuit.foodorderingsystem.model.response.GetAllMenuResponse;
import com.intuit.foodorderingsystem.repository.MenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantMenuRepository;
import com.intuit.foodorderingsystem.repository.RestaurantRepository;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class MenuServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private RestaurantMenuRepository restaurantMenuRepository;

    @InjectMocks
    private MenuServiceImpl menuService;


    @Test
    void testCreateMenu_Success() {
        List<CreateItem> itemList = Arrays.asList(
                CreateItem.builder()
                        .name("Item1   ")
                        .price((float) 23.4)
                        .foodType(FoodType.VEG).build(),
                CreateItem.builder()
                        .name("Item2  Space ")
                        .price((float) 23.1)
                        .foodType(FoodType.VEG)
                        .build()
        );

        CreateMenuRequest createMenuRequest = CreateMenuRequest.builder().itemList(itemList).build();
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                                        .id(1L)
                                        .restaurantType(RestaurantType.NON_VEG)
                                        .city("Bangalore")
                                        .name("Rest1")
                                        .state("Karnataka")
                                        .isActive(true)
                                        .build();
        MenuEntity menuEntity1 = new MenuEntity(2L, "item2 space", FoodType.VEG, null);
        MenuEntity menuEntity2 = new MenuEntity(3L, "item1", FoodType.VEG, null);
        when(restaurantRepository.findByIdAndIsActiveTrue(1L)).thenReturn(restaurantEntity);
        when(menuRepository.findByNameIn(anyList())).thenReturn(new ArrayList<>(List.of(menuEntity1)));
        when(menuRepository.saveAll(anyList())).thenReturn(new ArrayList<>(List.of(menuEntity2)));
        when(restaurantMenuRepository.saveAll(anyList())).thenReturn(null);

        CreateMenuResponse createMenuResponse = menuService.createMenu(1L,createMenuRequest);

        assertEquals(1L, createMenuResponse.getRestaurantId());

        List<CreateItem> itemListResponse = Arrays.asList(
                CreateItem.builder()
                        .name("item1")
                        .price((float) 23.4)
                        .foodType(FoodType.VEG).build(),
                CreateItem.builder()
                        .name("item2 space")
                        .price((float) 23.1)
                        .foodType(FoodType.VEG)
                        .build()
        );

        assertEquals(2, createMenuResponse.getItemList().size());
        assertIterableEquals(itemListResponse, createMenuResponse.getItemList());
    }

    @Test
    void testCreateMenuThrowDoNotExistException_Negative() {
        when(restaurantRepository.findByIdAndIsActiveTrue(1L)).thenReturn(null);
        DoNotExistException doNotExistException = assertThrows(DoNotExistException.class,
                () -> menuService.createMenu(1L,null));

        assertEquals(Messages.RESTAURANT_NOT_EXIST, doNotExistException.getMessage());

    }

    @Test
    void testCreateMenuIneligibleRequestException_Negative() {

        List<CreateItem> itemList = Arrays.asList(
                CreateItem.builder()
                        .name("Item1   ")
                        .price((float) 23.4)
                        .foodType(FoodType.VEG).build(),
                CreateItem.builder()
                        .name("Item2  Space ")
                        .price((float) 23.1)
                        .foodType(FoodType.NON_VEG)
                        .build()
        );

        CreateMenuRequest createMenuRequest = CreateMenuRequest.builder().itemList(itemList).build();
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .restaurantType(RestaurantType.VEG)
                .city("Bangalore")
                .name("Rest1")
                .state("Karnataka")
                .isActive(true)
                .build();

        when(restaurantRepository.findByIdAndIsActiveTrue(1L)).thenReturn(restaurantEntity);
        IneligibleRequestException ineligibleRequestException = assertThrows(IneligibleRequestException.class,
                () -> menuService.createMenu(1L,createMenuRequest));

        assertEquals(Messages.ALL_ITEMS_NOT_VEG, ineligibleRequestException.getMessage());

    }

    @Test
    void testCreateMenuIneligibleRequestExceptionWithRepeatedItems_Negative() {

        List<CreateItem> itemList = Arrays.asList(
                CreateItem.builder()
                        .name("Item1   ")
                        .price((float) 23.4)
                        .foodType(FoodType.VEG).build(),
                CreateItem.builder()
                        .name("item1")
                        .price((float) 23.1)
                        .foodType(FoodType.VEG)
                        .build()
        );

        CreateMenuRequest createMenuRequest = CreateMenuRequest.builder().itemList(itemList).build();
        RestaurantEntity restaurantEntity = RestaurantEntity.builder()
                .id(1L)
                .restaurantType(RestaurantType.NON_VEG)
                .city("Bangalore")
                .name("Rest1")
                .state("Karnataka")
                .isActive(true)
                .build();

        when(restaurantRepository.findByIdAndIsActiveTrue(1L)).thenReturn(restaurantEntity);
        IneligibleRequestException ineligibleRequestException = assertThrows(IneligibleRequestException.class,
                () -> menuService.createMenu(1L,createMenuRequest));

        assertEquals(Messages.HAS_REPEATED_ITEMS, ineligibleRequestException.getMessage());

    }

    @Test
    void getAllItems_Success() {
        MenuEntity menuEntity1 = new MenuEntity(2L, "item2 space", FoodType.VEG, null);
        MenuEntity menuEntity2 = new MenuEntity(3L, "item1", FoodType.VEG, null);
        Page<MenuEntity> menuEntityPage = new PageImpl<>(Arrays.asList(menuEntity1, menuEntity2));
        when(menuRepository.findAll((Pageable)any())).thenReturn(menuEntityPage);

        List<GetAllMenuResponse> getAllMenuResponses = menuService.getAllItems(0, 5);

        List<GetAllMenuResponse> getAllMenuResponseList = Arrays.asList(
                GetAllMenuResponse.builder()
                        .id(2L)
                        .foodType(FoodType.VEG)
                        .name("item2 space")
                        .build(),
                GetAllMenuResponse.builder()
                        .id(3L)
                        .foodType(FoodType.VEG)
                        .name("item1")
                        .build()
        );

        assertIterableEquals(getAllMenuResponseList, getAllMenuResponses);
    }


}