package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;

public class CategoryNotFoundException extends CustomAbstractException {

    public CategoryNotFoundException() {
        super(StatusEnum.CATEGORY_NOT_FOUND);
    }
}
