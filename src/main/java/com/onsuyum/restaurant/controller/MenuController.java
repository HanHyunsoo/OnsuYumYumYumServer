package com.onsuyum.restaurant.controller;

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
    public ResponseEntity<List<MenuResponse>> saveAll(@PathVariable Long id, @ModelAttribute MenuRequestForm menuRequestForm) {
        List<MenuResponse> menuResponses = menuService.saveAllWithRequest(id, menuRequestForm.getMenuRequestList(), true);

        return ResponseEntity.ok(menuResponses);
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<List<MenuResponse>> findAllByRestaurantId(@PathVariable Long id) {
        List<MenuResponse> menuResponses = menuService.findAllByRestaurantIdWithRequest(id, true);

        return ResponseEntity.ok(menuResponses);
    }

}
