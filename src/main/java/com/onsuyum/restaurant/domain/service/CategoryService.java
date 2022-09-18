package com.onsuyum.restaurant.domain.service;

import com.onsuyum.restaurant.domain.model.Category;
import com.onsuyum.restaurant.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category findOrCreateCategory(String name) {
        Category category = categoryRepository.findByName(name)
                .orElse(
                        Category.builder()
                                .name(name)
                                .build()
                );

        return categoryRepository.save(category);
    }

    @Transactional
    public List<Category> findOrCreateCategories(Set<String> categoryNames) {
        return categoryNames.stream()
                .map(this::findOrCreateCategory)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                String.format("카테고리(pk = %d) 정보 DB에 존재하지 않습니다.", id)
                        )
                );
    }

    @Transactional(readOnly = true)
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAllWithRestaurantCount(pageable);
    }

    @Transactional
    public void deleteById(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
