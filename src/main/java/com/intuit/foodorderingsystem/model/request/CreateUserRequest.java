package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.constant.RegexConstants;
import com.intuit.foodorderingsystem.enums.RestaurantType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {
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
    @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX)
    String contactNumber;
}
