package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class RestaurantTimeNotValidException extends CustomAbstractException {

    public RestaurantTimeNotValidException() {
        super(StatusEnum.RESTAURANT_TIME_NOT_VALID);
    }
}
