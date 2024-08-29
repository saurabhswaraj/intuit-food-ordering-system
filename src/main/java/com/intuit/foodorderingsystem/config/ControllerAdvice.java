package com.intuit.foodorderingsystem.config;

import com.intuit.foodorderingsystem.exception.AlreadyExistException;
import com.intuit.foodorderingsystem.exception.DoNotExistException;
import com.intuit.foodorderingsystem.exception.IneligibleRequestException;
import com.intuit.foodorderingsystem.exception.PhoneNumberNotValidException;
import com.intuit.foodorderingsystem.model.ErrorFields;
import com.intuit.foodorderingsystem.model.ErrorMessage;
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

    @ExceptionHandler({AlreadyExistException.class, DoNotExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    BaseResponseModel alreadyExistException(Exception exception) {
        ErrorMessageCommon errorMessage = getErrorMessage(exception);
        log.error(exception.toString());
        log.error(errorMessage.getMessage());
        return new BaseResponseModel<>(errorMessage);
    }

    @ExceptionHandler({PhoneNumberNotValidException.class, IneligibleRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BaseResponseModel validationErrorPhone(Exception exception) {
        ErrorMessageCommon errorMessage = getErrorMessage(exception);
        log.error(exception.toString());
        log.error(errorMessage.getMessage());
        return new BaseResponseModel<>(errorMessage);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    BaseResponseModel validationError(Exception exception) {

        BindingResult bindingResult = ((MethodArgumentNotValidException)exception).getBindingResult();

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<ErrorFields> errorFieldsList = new LinkedList<>();
        for(FieldError fieldError : fieldErrors) {
            errorFieldsList.add(new ErrorFields(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        log.error(exception.toString());
        return new BaseResponseModel<>(new ErrorMessageValidation(errorFieldsList));
    }


    private ErrorMessageCommon getErrorMessage(Exception exception) {
        return ErrorMessageCommon.builder()
                .message(Optional.ofNullable(exception.getMessage())
                        .orElse("Unexpected Error Occurred"))
                .build();
    }
}
