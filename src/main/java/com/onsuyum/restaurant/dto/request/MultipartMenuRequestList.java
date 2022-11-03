package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
