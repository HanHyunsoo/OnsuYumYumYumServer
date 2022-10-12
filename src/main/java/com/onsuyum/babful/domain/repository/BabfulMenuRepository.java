package com.onsuyum.babful.domain.repository;

import com.onsuyum.babful.domain.model.BabfulMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;

public interface BabfulMenuRepository extends PagingAndSortingRepository<BabfulMenu, Long> {

    boolean existsByMenuDate(LocalDate date);

    Page<BabfulMenu> findAllByMenuDateGreaterThanEqual(Pageable pageable, LocalDate date);

    Page<BabfulMenu> findAllByMenuDateLessThan(Pageable pageable, LocalDate date);
}