package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class LocalFileNotFoundException extends CustomAbstractException {

    public LocalFileNotFoundException() {
        super(StatusEnum.LOCAL_FILE_NOT_FOUND);
    }
}
