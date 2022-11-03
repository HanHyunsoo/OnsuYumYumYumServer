package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class ForbiddenRestaurantException extends CustomAbstractException {

    public ForbiddenRestaurantException() {
        super(StatusEnum.FORBIDDEN_RESTAURANT);
    }
}
