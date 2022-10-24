package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "ModelAttribute 전용 Menu Requests")
public class ModelAttributeMenuRequest {
    //    @ArraySchema(schema = @Schema(implementation = MenuRequest.class))
    @Schema(description = "메뉴 Request List", anyOf = MultipartMenuRequest.class)
    private List<MultipartMenuRequest> menuRequestList;

    @Builder
    public ModelAttributeMenuRequest(List<MultipartMenuRequest> menuRequestList) {
        this.menuRequestList = menuRequestList;
    }
}
