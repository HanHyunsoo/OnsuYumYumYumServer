package com.onsuyum.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema
public abstract class AbstractResponseBody {
    @Schema(description = "요청 시간")
    private final LocalDateTime timestamp;
    @Schema(description = "HTTP status")
    private final int status;
    @Schema(description = "HTTP status 세부 사항")
    private final String statusDetail;
    @Schema(description = "성공 또는 오류 코드")
    private final String code;
    @Schema(description = "해당 요청의 세부사항")
    private final String message;

    public AbstractResponseBody(int status, String statusDetail, String code, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.statusDetail = statusDetail;
        this.code = code;
        this.message = message;
    }
}
