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

    @ExceptionHandler(CouldNotSaveFileInLocal.class)
    public ResponseEntity<FailureResponseBody> handleCouldNotSaveFileInLocal(CouldNotSaveFileInLocal e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }

    @ExceptionHandler(CouldNotSaveFileInS3.class)
    public ResponseEntity<FailureResponseBody> handleCouldNotSaveFileInS3(CouldNotSaveFileInS3 e) {
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

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<FailureResponseBody> handleMenuNotFoundException(MenuNotFoundException e) {
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<FailureResponseBody> handleUserNotFoundException(UserNotFoundException e) {
        return FailureResponseBody.toResponseEntity(e.getStatusEnum());
    }
}
