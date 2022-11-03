package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class RestaurantNotFoundException extends CustomAbstractException {

    public RestaurantNotFoundException() {
        super(StatusEnum.RESTAURANT_NOT_FOUND);
    }
}
