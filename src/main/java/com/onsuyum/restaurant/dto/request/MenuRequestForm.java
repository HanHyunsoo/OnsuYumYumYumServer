package com.onsuyum.restaurant.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "메뉴 Request Form")
public class MenuRequestForm {
    //    @ArraySchema(schema = @Schema(implementation = MenuRequest.class))
    @Schema(description = "ㅎㅇ", anyOf = MenuRequest.class)
    private List<MenuRequest> menuRequestList;

    @Builder
    public MenuRequestForm(List<MenuRequest> menuRequestList) {
        this.menuRequestList = menuRequestList;
    }
}
