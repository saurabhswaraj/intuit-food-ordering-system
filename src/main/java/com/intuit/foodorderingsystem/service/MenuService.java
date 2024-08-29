package com.intuit.foodorderingsystem.service;


import com.intuit.foodorderingsystem.model.request.CreateMenuRequest;
import com.intuit.foodorderingsystem.model.response.*;

import java.util.List;

public interface MenuService {

    CreateMenuResponse createMenu (CreateMenuRequest createMenuRequest);
}
