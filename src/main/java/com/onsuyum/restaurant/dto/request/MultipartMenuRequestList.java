package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "메뉴 Request List(with multipart/form-data)")
public class MultipartMenuRequestList {
    @Schema(description = "메뉴 Request List", anyOf = MultipartMenuRequest.class)
    private List<MultipartMenuRequest> menuRequestList;

    @Builder
    public MultipartMenuRequestList(List<MultipartMenuRequest> menuRequestList) {
        this.menuRequestList = menuRequestList;
    }
}
