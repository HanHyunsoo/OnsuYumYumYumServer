package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class BabfulMenuDateAlreadyExistsException extends CustomAbstractException {
    public BabfulMenuDateAlreadyExistsException() {
        super(StatusEnum.BABFUL_MENU_DATE_ALREADY_EXISTS);
    }
}
