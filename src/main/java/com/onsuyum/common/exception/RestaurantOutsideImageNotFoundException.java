package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class RestaurantOutsideImageNotFoundException extends CustomAbstractException {
    public RestaurantOutsideImageNotFoundException() {
        super(StatusEnum.RESTAURANT_OUTSIDE_IMAGE_NOT_FOUND);
    }
}
