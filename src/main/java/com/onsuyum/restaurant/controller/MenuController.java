package com.onsuyum.restaurant.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.MenuService;
import com.onsuyum.restaurant.dto.request.MenuRequestForm;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{id}/menus")
    public ResponseEntity<SuccessResponseBody<List<MenuResponse>>> saveAll(@PathVariable Long id, @ModelAttribute MenuRequestForm menuRequestForm) {
        List<MenuResponse> menuResponses = menuService.saveAllWithRequest(id, menuRequestForm.getMenuRequestList(), true);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_CREATE_MENUS,
                        menuResponses
                );
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<SuccessResponseBody<List<MenuResponse>>> findAllByRestaurantId(@PathVariable Long id) {
        List<MenuResponse> menuResponses = menuService.findAllByRestaurantIdWithRequest(id, true);

        if (menuResponses.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_MENUS);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_MENUS,
                        menuResponses
                );
    }

}
