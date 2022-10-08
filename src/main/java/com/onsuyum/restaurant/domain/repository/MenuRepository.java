package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Menu;
import com.onsuyum.restaurant.domain.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends PagingAndSortingRepository<Menu, Long> {
    @Query(
            value = "select m " +
                    "from Menu m left join Restaurant r " +
                    "on m.restaurant = r " +
                    "where r.isRequest = :isRequest and m.price >= :price ",
            countQuery = "select count(m) " +
                    "from Menu m left join Restaurant r " +
                    "on m.restaurant = r " +
                    "where r.isRequest = :isRequest and m.price >= :price "
    )
    Page<Menu> findAllByRequestAndPriceGreaterThanEqual(Pageable pageable, @Param("isRequest") boolean isRequest, @Param("price") int price);
    List<Menu> findAllByRestaurant(Restaurant restaurant);
    void deleteAllByRestaurant(Restaurant restaurant);
}