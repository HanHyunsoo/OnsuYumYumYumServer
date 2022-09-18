package com.onsuyum.restaurant.domain.service;

import com.onsuyum.restaurant.domain.model.Category;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.model.RestaurantCategory;
import com.onsuyum.restaurant.domain.repository.RestaurantCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository restaurantCategoryRepository;

    @Transactional
    public List<RestaurantCategory> saveRestaurantCategories(Restaurant restaurant, List<Category> categories) {
        List<RestaurantCategory> restaurantCategories = categories.stream()
                .map(category -> restaurantCategoryRepository
                        .findByRestaurantAndCategory(restaurant, category)
                        .orElse(
                                RestaurantCategory.builder()
                                        .restaurant(restaurant)
                                        .category(category)
                                        .build()
                        ))
                .collect(Collectors.toList());

        return (List<RestaurantCategory>) restaurantCategoryRepository.saveAll(restaurantCategories);
    }

    @Transactional(readOnly = true)
    public Page<Restaurant> findAllRestaurantByCategory(Pageable pageable, Category category) {
        Page<RestaurantCategory> restaurantCategories = restaurantCategoryRepository.findAllByCategory(pageable, category);

        return restaurantCategories.map(RestaurantCategory::getRestaurant);
    }

    @Transactional(readOnly = true)
    public Page<Category> findAllCategoryByRestaurant(Pageable pageable, Restaurant restaurant) {
        Page<RestaurantCategory> restaurantCategories = restaurantCategoryRepository.findAllByRestaurant(pageable, restaurant);

        return restaurantCategories.map(RestaurantCategory::getCategory);
    }
}
