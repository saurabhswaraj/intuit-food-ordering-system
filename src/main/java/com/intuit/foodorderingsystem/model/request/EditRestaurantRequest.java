package com.intuit.foodorderingsystem.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    Integer maxOrderCapacity;
    String contactNumber;
}
