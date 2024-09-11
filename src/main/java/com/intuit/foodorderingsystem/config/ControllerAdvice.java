package com.intuit.foodorderingsystem.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intuit.foodorderingsystem.exception.*;
import com.intuit.foodorderingsystem.model.ErrorFields;
import com.intuit.foodorderingsystem.model.ErrorMessageCommon;
import com.intuit.foodorderingsystem.model.ErrorMessageValidation;
import com.intuit.foodorderingsystem.model.response.BaseResponseModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ExceptionHandler({AlreadyExistException.class, DoNotExistException.class, OrderCanNotBeCreatedException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    BaseResponseModel<Object> alreadyExistException(Exception exception) {
        ErrorMessageCommon errorMessage = getErrorMessage(exception);
        log.error(errorMessage.getMessage(), exception);
        return new BaseResponseModel<>(errorMessage);
    }

    @ExceptionHandler({PhoneNumberNotValidException.class, IneligibleRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BaseResponseModel<Object> validationErrorPhone(Exception exception) {
        ErrorMessageCommon errorMessage = getErrorMessage(exception);
        log.error(errorMessage.getMessage(), exception);
        return new BaseResponseModel<>(errorMessage);
    }

    @ExceptionHandler({JsonProcessingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BaseResponseModel<Object> jsonBadRequest(Exception exception) {
        ErrorMessageCommon errorMessage = ErrorMessageCommon.builder().message("Unknown properties exist in the request").build();
        log.error(errorMessage.getMessage(), exception);
        return new BaseResponseModel<>(errorMessage);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BaseResponseModel<Object> validationError(Exception exception) {

        BindingResult bindingResult = ((MethodArgumentNotValidException)exception).getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<ErrorFields> errorFieldsList = new LinkedList<>();
        for(FieldError fieldError : fieldErrors) {
            errorFieldsList.add(new ErrorFields(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        log.error("Validation Error Occurred", exception);
        return new BaseResponseModel<>(new ErrorMessageValidation(errorFieldsList));
    }



    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    BaseResponseModel<Object> genericException(Exception exception) {
        log.error("Something went Wrong", exception);
        return new BaseResponseModel<>(getErrorMessage(exception));
    }


    private ErrorMessageCommon getErrorMessage(Exception exception) {
        return ErrorMessageCommon.builder()
                .message(Optional.ofNullable(exception.getMessage())
                        .orElse("Unexpected Error Occurred"))
                .build();
    }
}
