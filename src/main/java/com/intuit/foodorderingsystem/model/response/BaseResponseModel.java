package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.model.ErrorMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class BaseResponseModel<T> {

    private ZonedDateTime time;
    private T data;
    private ErrorMessage errorMessage;

    public BaseResponseModel(T data) {
        this.data = data;
        this.time = ZonedDateTime.now();
    }

    public BaseResponseModel(ErrorMessage errorMessage) {
        this.time = ZonedDateTime.now();
        this.errorMessage = errorMessage;
    }


}
