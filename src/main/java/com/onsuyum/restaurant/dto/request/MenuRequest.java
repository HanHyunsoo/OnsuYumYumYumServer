package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "메뉴 Request")
public class MenuRequest {
    @Schema(description = "메뉴 ID(수정 할 때만 사용할 것)", example = "1")
    private Long id;
    @Schema(description = "이름", example = "물냉면", required = true)
    private String name;
    @Schema(description = "가격", example = "10000")
    private Integer price;
    @Schema(description = "간단한 설명", example = "시원한 육수가 있는 냉면")
    private String description;
    @Schema(description = "사진")
    private MultipartFile menuImage;

    @Builder
    public MenuRequest(Long id, String name, Integer price, String description, MultipartFile menuImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.menuImage = menuImage;
    }
}
