package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Category;
import com.onsuyum.restaurant.domain.model.Restaurant;
import com.onsuyum.restaurant.domain.model.RestaurantCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RestaurantCategoryRepository extends PagingAndSortingRepository<RestaurantCategory, Long> {
    Optional<RestaurantCategory> findByRestaurantAndCategory(Restaurant restaurant, Category category);

    Page<RestaurantCategory> findAllByCategory(Pageable pageable, Category category);

    @Query(
            value = "select rc " +
                    "from RestaurantCategory rc left join Category c " +
                    "on rc.category = c " +
                    "where rc.restaurant.isRequest = false ",
            countQuery = "select count(rc) from RestaurantCategory rc"
    )
    Page<RestaurantCategory> findAllByCategoryWithRequest(Pageable pageable, Category category);

    Page<RestaurantCategory> findAllByRestaurant(Pageable pageable, Restaurant restaurant);
}