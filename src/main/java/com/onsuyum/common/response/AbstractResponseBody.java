package com.onsuyum.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public abstract class AbstractResponseBody {
    private final LocalDateTime timestamp;
    private final int status;
    private final String statusDetail;
    private final String code;
    private final String message;

    public AbstractResponseBody(int status, String statusDetail, String code, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.statusDetail = statusDetail;
        this.code = code;
        this.message = message;
    }
}
