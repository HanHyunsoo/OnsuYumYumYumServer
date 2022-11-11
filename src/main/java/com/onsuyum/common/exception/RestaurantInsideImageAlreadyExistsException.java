package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class RestaurantInsideImageAlreadyExistsException extends CustomAbstractException {

    public RestaurantInsideImageAlreadyExistsException() {
        super(StatusEnum.RESTAURANT_INSIDE_IMAGE_ALREADY_EXISTS);
    }
}
