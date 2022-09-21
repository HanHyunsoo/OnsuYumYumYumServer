package com.onsuyum.restaurant.domain.service;

import com.onsuyum.restaurant.domain.model.Category;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.model.RestaurantCategory;
import com.onsuyum.restaurant.domain.repository.CategoryRepository;
import com.onsuyum.restaurant.domain.repository.RestaurantCategoryRepository;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantCategoryService {

    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final RestaurantCategoryRepository restaurantCategoryRepository;

    @Transactional
    public Map<String, Object> saveAllRestaurantCategory(Long id, Set<String> categoryNames) {
        return saveAllRestaurantCategoryWithRequest(id, categoryNames, false);
    }

    @Transactional
    public Map<String, Object> saveAllRestaurantCategoryWithRequest(Long id, Set<String> categoryNames, boolean checkRequest) {
        Map<String, Object> responseMap = new HashMap<>();

        Restaurant restaurant = restaurantService.findEntityById(id);

        if (checkRequest) {
            restaurantService.validIsRequest(restaurant);
            restaurantService.validTime(restaurant);
        }

        List<Category> categories = categoryService.findEntityOrCreateCategories(categoryNames);

        responseMap.put("restaurant", restaurant.toResponseDTO());
        responseMap.put(
                "categories",
                categories.stream()
                        .map(Category::toResponseDTO)
                        .collect(Collectors.toList())
        );

        List<RestaurantCategory> restaurantCategories = categories.stream()
                .map(category -> restaurantCategoryRepository
                        .findByRestaurantAndCategory(restaurant, category)
                        .orElseGet(() ->
                                RestaurantCategory.builder()
                                        .restaurant(restaurant)
                                        .category(category)
                                        .build()
                        ))
                .collect(Collectors.toList());

        restaurantCategoryRepository.saveAll(restaurantCategories);

        return responseMap;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findAllRestaurantByCategoryId(Pageable pageable, Long id) {
        Category category = categoryService.findEntityById(id);

        Page<RestaurantCategory> restaurantCategories = restaurantCategoryRepository.findAllByCategory(pageable, category);

        return restaurantCategories
                .map(RestaurantCategory::getRestaurant)
                .map(Restaurant::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<RestaurantResponse> findAllRestaurantByCategoryIdWithRequest(Pageable pageable, Long id) {
        Category category = categoryService.findEntityById(id);

        Page<RestaurantCategory> restaurantCategories = restaurantCategoryRepository.findAllByCategoryWithRequest(pageable, category);

        return restaurantCategories
                .map(RestaurantCategory::getRestaurant)
                .map(Restaurant::toResponseDTO);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAllCategoryByRestaurantId(Pageable pageable, Long id) {
        return findAllCategoryByRestaurantIdWithRequest(pageable, id, false);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAllCategoryByRestaurantIdWithRequest(Pageable pageable, Long id, boolean checkRequest) {
        Restaurant restaurant = restaurantService.findEntityById(id);

        if (checkRequest) {
            restaurantService.validIsRequest(restaurant);
        }

        Page<RestaurantCategory> restaurantCategories = restaurantCategoryRepository.findAllByRestaurant(pageable, restaurant);

        return restaurantCategories
                .map(RestaurantCategory::getCategory)
                .map(Category::toResponseDTO);
    }


}
