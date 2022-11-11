package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class MenuNotFoundException extends CustomAbstractException {

    public MenuNotFoundException() {
        super(StatusEnum.MENU_NOT_FOUND);
    }
}
