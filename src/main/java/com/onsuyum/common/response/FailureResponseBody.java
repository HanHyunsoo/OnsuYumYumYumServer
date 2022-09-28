package com.onsuyum.common.response;

import com.onsuyum.common.StatusEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class FailureResponseBody extends AbstractResponseBody {

    @Builder
    public FailureResponseBody(int status, String statusDetail, String code, String message) {
        super(status, statusDetail, code, message);
    }

    public static ResponseEntity<FailureResponseBody> toResponseEntity(StatusEnum statusEnum) {

        return ResponseEntity
                .status(statusEnum.getHttpStatus())
                .body(FailureResponseBody.builder()
                        .status(statusEnum.getHttpStatus().value())
                        .statusDetail(statusEnum.getHttpStatus().name())
                        .code(statusEnum.name())
                        .message(statusEnum.getDetail())
                        .build()
                );
    }
}
