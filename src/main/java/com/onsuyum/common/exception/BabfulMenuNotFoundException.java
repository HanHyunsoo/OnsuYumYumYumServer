package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class BabfulMenuNotFoundException extends CustomAbstractException {

    public BabfulMenuNotFoundException() {
        super(StatusEnum.BABFUL_MENU_NOT_FOUND);
    }
}
