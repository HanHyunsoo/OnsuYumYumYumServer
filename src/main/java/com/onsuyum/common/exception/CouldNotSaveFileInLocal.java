package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class CouldNotSaveFileInLocal extends CustomAbstractException {

    public CouldNotSaveFileInLocal() {
        super(StatusEnum.COULD_NOT_SAVE_FILE_IN_LOCAL);
    }
}
