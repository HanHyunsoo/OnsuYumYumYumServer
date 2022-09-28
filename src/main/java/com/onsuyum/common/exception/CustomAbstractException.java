package com.onsuyum.common.exception;

import com.onsuyum.common.StatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class CustomAbstractException extends RuntimeException {
    protected final StatusEnum statusEnum;
}
