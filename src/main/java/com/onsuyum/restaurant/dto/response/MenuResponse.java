package com.onsuyum.restaurant.dto.response;

import com.onsuyum.storage.dto.response.ImageFileResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@Schema(description = "메뉴 Response")
@SuperBuilder
public class MenuResponse {

    @Schema(description = "메뉴 ID", example = "1")
    private final Long id;
    @Schema(description = "이름", example = "삼겹살")
    private final String name;
    @Schema(description = "가격", example = "8000")
    private final Integer price;
    @Schema(description = "설명", example = "맛있는 삼겹살")
    private final String description;
    @Schema(description = "이미지")
    private final ImageFileResponse menuImage;
    @Schema(description = "생성 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime createdDate;
    @Schema(description = "수정 시간", example = "2022-05-11 00:00:00")
    private final LocalDateTime modifiedDate;

    public MenuResponse(Long id, String name, Integer price, String description,
            ImageFileResponse menuImage, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.menuImage = menuImage;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}