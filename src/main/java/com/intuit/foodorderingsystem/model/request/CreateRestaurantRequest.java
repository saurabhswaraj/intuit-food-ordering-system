package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.RegexConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateRestaurantRequest {
    @NotBlank
    String name;
    @NotBlank
    String address;
    @NotBlank
    String city;
    @NotBlank
    String state;
    @NotBlank
    String pinCode;
    Integer maxOrderCapacity;
    @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX)
    String contactNumber;
}
