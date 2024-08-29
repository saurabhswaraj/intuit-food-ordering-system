package com.intuit.foodorderingsystem.controller;

import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.response.BaseResponseModel;
import com.intuit.foodorderingsystem.model.response.CreateMenuResponse;
import com.intuit.foodorderingsystem.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/menu")
@Log4j2
@RequiredArgsConstructor
public class MenuController {

    @Autowired
    private final MenuService menuService;

    @PostMapping
    BaseResponseModel<CreateMenuResponse> createMenu (@Valid @RequestBody CreateMenuRequest createMenuRequest) {
        return new BaseResponseModel<>(menuService.createMenu(createMenuRequest));
    }
}
