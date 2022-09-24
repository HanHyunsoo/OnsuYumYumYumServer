package com.onsuyum.restaurant.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "카테고리 Response")
public class CategoryResponse {
    @Schema(description = "카테고리 ID", example = "1")
    private final Long id;
    @Schema(description = "설명", example = "가성비")
    private final String name;
    @Schema(description = "생성 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime createdDate;
    @Schema(description = "수정 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime modifiedDate;

    @Builder
    public CategoryResponse(Long id, String name, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}