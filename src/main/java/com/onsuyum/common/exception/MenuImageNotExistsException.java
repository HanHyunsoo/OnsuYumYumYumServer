package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class MenuImageNotExistsException extends CustomAbstractException {

    public MenuImageNotExistsException() {
        super(StatusEnum.MENU_IMAGE_NOT_FOUND);
    }
}
