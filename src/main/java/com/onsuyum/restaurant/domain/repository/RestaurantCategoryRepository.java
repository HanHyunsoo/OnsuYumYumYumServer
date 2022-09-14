package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.RestaurantCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantCategoryRepository extends PagingAndSortingRepository<RestaurantCategory, Long> {
}