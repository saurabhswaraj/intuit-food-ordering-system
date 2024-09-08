package com.intuit.foodorderingsystem.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditRestaurantRequest {
    String name;
    String address;
    String city;
    String state;
    String pinCode;
    String contactNumber;
}
