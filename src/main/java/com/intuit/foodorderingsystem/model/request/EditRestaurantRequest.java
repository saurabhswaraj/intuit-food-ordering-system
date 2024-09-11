package com.intuit.foodorderingsystem.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class EditRestaurantRequest {
    private String name;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String contactNumber;
}
