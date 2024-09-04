package com.intuit.foodorderingsystem.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.constant.RegexConstants;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRestaurantRequest {
    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    String name;
    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    String address;
    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    String city;
    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    String state;
    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    String pinCode;
    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    Integer maxOrderCapacity;
    @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX)
    String contactNumber;

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    RestaurantType restaurantType;
}
