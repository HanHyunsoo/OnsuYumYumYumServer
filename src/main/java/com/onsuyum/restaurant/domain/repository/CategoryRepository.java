package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Category;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Query(
            value = "select c " +
                    "from Category c left join RestaurantCategory rc " +
                    "on c.id = rc.category.id " +
                    "group by c " +
                    "order by count(rc) desc ",
            countQuery = "select count(c) " +
                    "from Category c left join RestaurantCategory rc " +
                    "on c.id = rc.category.id " +
                    "group by c "
    )
    Page<Category> findAllWithRestaurantCount(Pageable pageable);
}