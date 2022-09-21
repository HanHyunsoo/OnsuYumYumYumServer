package com.onsuyum.restaurant.controller;

import com.onsuyum.restaurant.domain.service.RestaurantService;
import com.onsuyum.restaurant.dto.request.RestaurantRequest;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> saveRestaurantWithRequest(@ModelAttribute RestaurantRequest dto) {
        RestaurantResponse restaurantResponse = restaurantService.save(dto, true);

        return ResponseEntity.ok(restaurantResponse);
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> findAllRestaurantWithNotRequest(Pageable pageable,
                                                                                    @RequestParam(name = "keyword", required = false) String name) {
        Page<RestaurantResponse> restaurantResponsePage;
        if (name.isBlank()) {
            restaurantResponsePage = restaurantService.findAllByRequest(pageable, false);
        } else {
            restaurantResponsePage = restaurantService.findAllByNameAndRequest(pageable, name, false);
        }

        if (restaurantResponsePage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(restaurantResponsePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findRestaurantWithNotRequest(@PathVariable Long id) {
        RestaurantResponse restaurantResponse = restaurantService.findByIdWithRequest(id, true);

        return ResponseEntity.ok(restaurantResponse);
    }

    @GetMapping("/random")
    public ResponseEntity<RestaurantResponse> findRandomRestaurantWithNotRequest() {
        RestaurantResponse restaurantResponse = restaurantService.findRandomRestaurant();

        return ResponseEntity.ok(restaurantResponse);
    }
}