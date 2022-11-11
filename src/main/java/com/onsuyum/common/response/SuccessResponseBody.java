package com.onsuyum.common.response;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import com.onsuyum.storage.dto.response.ImageFileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class SuccessResponseBody<T> extends AbstractResponseBody {

    @Schema(description = "데이터 내용", anyOf = {CategoryResponse.class, MenuResponse.class,
            RestaurantResponse.class, ImageFileResponse.class, List.class})
    private final T data;

    @Builder
    public SuccessResponseBody(int status, String statusDetail, String code, String message,
            T data) {
        super(status, statusDetail, code, message);
        this.data = data;
    }

    public static <T> ResponseEntity<SuccessResponseBody<T>> toResponseEntity(StatusEnum statusEnum,
            T data) {
        return ResponseEntity
                .status(statusEnum.getHttpStatus())
                .body(SuccessResponseBody.<T>builder()
                                         .status(statusEnum.getHttpStatus()
                                                           .value())
                                         .statusDetail(statusEnum.getHttpStatus()
                                                                 .name())
                                         .code(statusEnum.name())
                                         .message(statusEnum.getDetail())
                                         .data(data)
                                         .build()
                );
    }

    public static <T> ResponseEntity<SuccessResponseBody<T>> toEmptyResponseEntity(
            StatusEnum statusEnum) {
        return ResponseEntity
                .status(statusEnum.getHttpStatus())
                .body(SuccessResponseBody.<T>builder()
                                         .status(statusEnum.getHttpStatus()
                                                           .value())
                                         .statusDetail(statusEnum.getHttpStatus()
                                                                 .name())
                                         .code(statusEnum.name())
                                         .message(statusEnum.getDetail())
                                         .data(null)
                                         .build()
                );
    }
}
