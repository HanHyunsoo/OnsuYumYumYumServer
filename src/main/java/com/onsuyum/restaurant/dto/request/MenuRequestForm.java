package com.onsuyum.restaurant.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuRequestForm {
    private List<MenuRequest> menuRequestList;

    @Builder
    public MenuRequestForm(List<MenuRequest> menuRequestList) {
        this.menuRequestList = menuRequestList;
    }
}
