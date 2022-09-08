package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Menu;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {
}