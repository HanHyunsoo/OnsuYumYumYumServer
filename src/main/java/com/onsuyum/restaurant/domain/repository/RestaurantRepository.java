package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Restaurant;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {
}