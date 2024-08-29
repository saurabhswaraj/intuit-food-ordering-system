package com.intuit.foodorderingsystem.model.response;

import com.intuit.foodorderingsystem.model.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponseModel<T> {

    private ZonedDateTime time = ZonedDateTime.now();
    private T data;
    private ErrorMessage errorMessage;

    public BaseResponseModel(T data) {
        this.data = data;
        this.errorMessage = null;
    }

    public BaseResponseModel(ErrorMessage errorMessage) {
        this.data = null;
        this.errorMessage = errorMessage;
    }


}
