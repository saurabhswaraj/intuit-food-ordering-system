package com.intuit.foodorderingsystem.model.request;

import com.intuit.foodorderingsystem.constant.Messages;
import com.intuit.foodorderingsystem.constant.RegexConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class CreateUserRequest {

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

    @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX)
    private String contactNumber;
}
