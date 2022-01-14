package com.big.assessment.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class UserBadRequestException extends RuntimeException {

    @Getter
    private HttpStatus errorCode;

    public UserBadRequestException(HttpStatus errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }
}
