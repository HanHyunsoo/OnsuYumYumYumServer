package com.onsuyum.restaurant.domain.repository;

import com.onsuyum.restaurant.domain.model.Hashtag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HashtagRepository extends PagingAndSortingRepository<Hashtag, Long> {
}