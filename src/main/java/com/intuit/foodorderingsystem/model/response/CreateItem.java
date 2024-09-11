package com.intuit.foodorderingsystem.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intuit.foodorderingsystem.enums.FoodType;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateItem {
    private String name;
    private Float price;
    private FoodType foodType;
}
