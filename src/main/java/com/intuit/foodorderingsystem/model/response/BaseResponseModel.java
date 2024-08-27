package com.intuit.foodorderingsystem.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class BaseResponseModel<T> {

    private ZonedDateTime time = ZonedDateTime.now();
    private T data;

    public BaseResponseModel(T data) {
        this.data = data;
    }

}
