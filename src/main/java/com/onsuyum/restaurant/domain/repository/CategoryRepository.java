package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
}