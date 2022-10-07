package com.onsuyum.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "페이지")
public class CustomPageable {

    @Schema(description = "페이지 번호(0 ~ N)", example = "2", defaultValue = "0")
    private Integer page;

    @Schema(description = "페이지 크기", allowableValues = "range[0, 100]", defaultValue = "10")
    private Integer size;

    @Schema(description = "정렬(사용법: 컬럼명, ASC|DESC)", example = "price, desc, id, asc")
    private List<String> sort;

    @Builder
    public CustomPageable(Integer page, Integer size, List<String> sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
}
