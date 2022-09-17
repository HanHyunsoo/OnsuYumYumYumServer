package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long> {

    @Query(
            value = "select r from Restaurant r where r.id = ?1 and r.isRequest = ?2"
    )
    Optional<Restaurant> findByIdAndRequest(Long id, boolean isRequest);

    @Query(
            value = "select r " +
                    "from Restaurant r left join RestaurantCategory rc " +
                    "on r.id = rc.restaurant.id " +
                    "where r.isRequest = :isRequest " +
                    "group by r " +
                    "order by count(rc) desc ",
            countQuery = "select count(r) from Restaurant r"
    )
    Page<Restaurant> findAllByRequestWithCategoryCount(Pageable pageable, @Param("isRequest") boolean isRequest);
}