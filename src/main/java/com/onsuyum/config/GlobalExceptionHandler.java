package com.onsuyum.config;

import com.onsuyum.common.exception.*;
import com.onsuyum.common.response.FailureResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.onsuyum")
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<FailureResponseBody> handleCategoryNotFoundException(CategoryNotFoundException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }

    @ExceptionHandler(CouldNotLoadImageFileException.class)
    public ResponseEntity<FailureResponseBody> handleCouldNotLoadImageFileException(CouldNotLoadImageFileException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }

    @ExceptionHandler(ForbiddenRestaurantException.class)
    public ResponseEntity<FailureResponseBody> handleForbiddenRestaurantException(ForbiddenRestaurantException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<FailureResponseBody> handleImageNotFoundException(ImageNotFoundException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }

    @ExceptionHandler(LocalFileNotFoundException.class)
    public ResponseEntity<FailureResponseBody> handleLocalFileNotFoundException(LocalFileNotFoundException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<FailureResponseBody> handleRestaurantNotFoundException(RestaurantNotFoundException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }

    @ExceptionHandler(RestaurantTimeNotValidException.class)
    public ResponseEntity<FailureResponseBody> handleRestaurantTimeNotValidException(RestaurantTimeNotValidException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }
}
