package com.onsuyum.babful.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Schema(description = "밥풀 식단 Request")
public class BabfulMenuRequest {

    @Schema(description = "밥풀 식단 날짜", example = "2022-10-11")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate menuDate;
    @Schema(description = "밥풀 식단 각 음식들", example = "[ \"밥\", \"국\", \"김치\"]")
    private final List<String> foods;
    @Schema(description = "밥풀 식단 중 맛있는 음식", example = "김치")
    private final String deliciousFood;

    @Builder
    public BabfulMenuRequest(LocalDate menuDate, List<String> foods, String deliciousFood) {
        this.menuDate = menuDate;
        this.foods = foods;
        this.deliciousFood = deliciousFood;
    }
}
