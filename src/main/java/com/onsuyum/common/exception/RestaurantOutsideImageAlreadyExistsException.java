package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class RestaurantOutsideImageAlreadyExistsException extends CustomAbstractException {

    public RestaurantOutsideImageAlreadyExistsException() {
        super(StatusEnum.RESTAURANT_OUTSIDE_IMAGE_ALREADY_EXISTS);
    }
}
