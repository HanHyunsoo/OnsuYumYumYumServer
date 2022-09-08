package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.RestaurantHashTag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantHashTagRepository extends PagingAndSortingRepository<RestaurantHashTag, Long> {
}