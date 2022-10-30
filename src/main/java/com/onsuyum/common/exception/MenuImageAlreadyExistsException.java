package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class MenuImageAlreadyExistsException extends CustomAbstractException {
    public MenuImageAlreadyExistsException() {
        super(StatusEnum.MENU_IMAGE_ALREADY_EXISTS);
    }
}
