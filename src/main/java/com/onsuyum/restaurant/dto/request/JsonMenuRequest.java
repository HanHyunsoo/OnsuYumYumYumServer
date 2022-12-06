package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter // TODO: 2022/12/06 가변객체 처리하기
@NoArgsConstructor
@Schema(description = "메뉴 Request(with application/json)")
@SuperBuilder
public class JsonMenuRequest {

    @Schema(description = "이름", example = "물냉면", required = true)
    private String name;
    @Schema(description = "가격", example = "10000")
    private Integer price;
    @Schema(description = "간단한 설명", example = "시원한 육수가 있는 냉면")
    private String description;

    public JsonMenuRequest(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
