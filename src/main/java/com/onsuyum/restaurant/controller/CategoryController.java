package com.onsuyum.restaurant.controller;

import com.onsuyum.restaurant.domain.model.Category;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.service.CategoryService;
import com.onsuyum.restaurant.domain.service.RestaurantCategoryService;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final RestaurantCategoryService restaurantCategoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> findAll(
            @SortDefault(
                    sort = "restaurantCount",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        Page<Category> categories = categoryService.findAll(pageable);

        if (categories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(categories.map(Category::toResponseDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        Category category = categoryService.findById(id);

        return ResponseEntity.ok(category.toResponseDTO());
    }

    @GetMapping("/{id}/restaurants")
    public ResponseEntity<Page<RestaurantResponse>> findAllRestaurantByCategoryId(@PathVariable Long id, Pageable pageable) {
        Page<Restaurant> restaurants = restaurantCategoryService.findAllRestaurantByCategoryId(pageable, id);

        if (restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(restaurants.map(Restaurant::toResponseDTO));
    }
}
