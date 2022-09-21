package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Menu;
import com.onsuyum.restaurant.domain.model.Restaurant;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {
    List<Menu> findAllByRestaurant(Restaurant restaurant);
    void deleteAllByRestaurant(Restaurant restaurant);
}