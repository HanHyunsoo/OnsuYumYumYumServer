package com.onsuyum.babful.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Schema(description = "밥풀 식단 Response")
public class BabfulMenuResponse {
    @Schema(description = "밥풀 식단 id")
    private final Long id;
    @Schema(description = "밥풀 식단 날짜")
    private final LocalDate menuDate;
    @Schema(description = "밥풀 식단 각 음식들")
    private final List<String> foods;
    @Schema(description = "밥풀 식단 중 맛있는 음식")
    private final String deliciousFood;
    @Schema(description = "생성 시간")
    private final LocalDateTime createdDate;
    @Schema(description = "수정 시간")
    private final LocalDateTime modifiedDate;

    @Builder
    public BabfulMenuResponse(Long id, LocalDate menuDate, List<String> foods, String deliciousFood, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.menuDate = menuDate;
        this.foods = foods;
        this.deliciousFood = deliciousFood;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}