package com.onsuyum.restaurant.controller;

import com.onsuyum.restaurant.domain.model.Category;
import com.onsuyum.restaurant.domain.model.Menu;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.service.CategoryService;
import com.onsuyum.restaurant.domain.service.MenuService;
import com.onsuyum.restaurant.domain.service.RestaurantCategoryService;
import com.onsuyum.restaurant.domain.service.RestaurantService;
import com.onsuyum.restaurant.dto.request.MenuRequestForm;
import com.onsuyum.restaurant.dto.request.RestaurantRequest;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final CategoryService categoryService;
    private final MenuService menuService;
    private final RestaurantService restaurantService;
    private final RestaurantCategoryService restaurantCategoryService;

    @PostMapping
    public ResponseEntity<RestaurantResponse> saveRestaurantWithRequest(@ModelAttribute RestaurantRequest dto) {
        Restaurant restaurant = restaurantService.save(dto, true);
        List<Category> categories = categoryService.findOrCreateCategories(dto.getCategoryNames());
        restaurantCategoryService.saveRestaurantCategories(restaurant, categories);

        return ResponseEntity.ok(restaurant.toResponseDTO());
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantResponse>> findAllRestaurantWithNotRequest(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantService.findAllByRequest(pageable, false);

        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(restaurants.map(Restaurant::toResponseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> findRestaurantWithNotRequest(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findByIdAndIsRequest(id, false);

        return ResponseEntity.ok(restaurant.toResponseDTO());
    }


    @PostMapping("/{id}/menus")
    public ResponseEntity<List<MenuResponse>> saveAllMenusByRestaurantIdWithRequest(@PathVariable Long id, @ModelAttribute MenuRequestForm dto) {
        Restaurant restaurant = restaurantService.findById(id);
        List<Menu> menus = menuService.saveAllWithRequest(restaurant, dto.getMenuRequestList());

        return ResponseEntity.ok(menus.stream().map(Menu::toResponseDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}/menus")
    public ResponseEntity<List<MenuResponse>> findAllByRestaurantId(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        List<Menu> menus = menuService.findAllByRestaurant(restaurant);

        return ResponseEntity.ok(menus.stream().map(Menu::toResponseDTO).collect(Collectors.toList()));
    }
}