package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class CouldNotSaveFileInS3 extends CustomAbstractException {
    public CouldNotSaveFileInS3() {
        super(StatusEnum.COULD_NOT_SAVE_FILE_IN_S3);
    }
}
