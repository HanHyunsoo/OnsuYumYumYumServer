package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class LoginFailException extends CustomAbstractException {

    public LoginFailException() {
        super(StatusEnum.LOGIN_FAIL);
    }
}
