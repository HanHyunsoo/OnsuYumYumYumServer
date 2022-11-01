package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class RestaurantInsideImageNotFoundException extends CustomAbstractException {
    public RestaurantInsideImageNotFoundException() {
        super(StatusEnum.RESTAURANT_INSIDE_IMAGE_NOT_FOUND);
    }
}
