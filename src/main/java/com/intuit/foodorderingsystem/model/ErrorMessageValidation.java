package com.intuit.foodorderingsystem.model;

import lombok.*;

import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorMessageValidation extends ErrorMessage {

    private List<ErrorFields> errorFieldsList;

}
