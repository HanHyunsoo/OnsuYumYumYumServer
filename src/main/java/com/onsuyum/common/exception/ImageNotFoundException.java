package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class ImageNotFoundException extends CustomAbstractException {

    public ImageNotFoundException() {
        super(StatusEnum.IMAGE_NOT_FOUND);
    }
}
