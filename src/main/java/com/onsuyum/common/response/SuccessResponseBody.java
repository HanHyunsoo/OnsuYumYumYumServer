package com.onsuyum.common.response;

import com.onsuyum.common.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class SuccessResponseBody extends AbstractResponseBody {
    private final Object data;

    @Builder
    public SuccessResponseBody(int status, String statusDetail, String code, String message, Object data) {
        super(status, statusDetail, code, message);
        this.data = data;
    }

    public static ResponseEntity<SuccessResponseBody> toResponseEntity(StatusEnum statusEnum, Object data) {
        return ResponseEntity
                .status(statusEnum.getHttpStatus())
                .body(SuccessResponseBody.builder()
                        .status(statusEnum.getHttpStatus().value())
                        .statusDetail(statusEnum.getHttpStatus().name())
                        .code(statusEnum.name())
                        .message(statusEnum.getDetail())
                        .data(data)
                        .build()
                );
    }
}
