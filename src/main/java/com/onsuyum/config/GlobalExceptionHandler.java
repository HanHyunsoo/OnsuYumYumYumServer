package com.onsuyum.config;

import com.onsuyum.common.exception.CustomAbstractException;
import com.onsuyum.common.response.FailureResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.onsuyum")
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAbstractException.class)
    public ResponseEntity<FailureResponseBody> handelExceptions(CustomAbstractException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }
}
