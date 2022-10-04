package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class UserNotFoundException extends CustomAbstractException {
    public UserNotFoundException() {
        super(StatusEnum.USER_NOT_FOUND);
    }
}
