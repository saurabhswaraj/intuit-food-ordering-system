package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.constant.RegexConstants;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class CreateRestaurantRequest {

    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    private String name;

    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    private String address;

    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    private String city;

    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    private String state;

    @NotBlank(message = Messages.VALUE_CAN_NOT_BE_EMPTY)
    private String pinCode;

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    private Integer maxOrderCapacity;

    @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX)
    private String contactNumber;

    @NotNull(message = Messages.VALUE_CAN_NOT_BE_NULL)
    private RestaurantType restaurantType;
}
