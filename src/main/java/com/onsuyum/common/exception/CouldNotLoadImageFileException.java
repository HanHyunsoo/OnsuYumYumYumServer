package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class CouldNotLoadImageFileException extends CustomAbstractException {
    public CouldNotLoadImageFileException() {
        super(StatusEnum.COULD_NOT_LOAD_IMAGE_FILE);
    }
}
