package com.intuit.foodorderingsystem.builder;

import com.intuit.foodorderingsystem.entity.MenuEntity;
import com.intuit.foodorderingsystem.model.response.GetAllMenuResponse;

public class GetAllMenuResponseBuilderFactory {

    public static GetAllMenuResponse build (MenuEntity menuEntity) {

        return GetAllMenuResponse.builder()
                .id(menuEntity.getId())
                .name(menuEntity.getName())
                .foodType(menuEntity.getFoodType())
                .build();
    }
}
