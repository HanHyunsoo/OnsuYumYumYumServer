package com.onsuyum.restaurant.domain.service;

import com.onsuyum.common.exception.CategoryNotFoundException;
import com.onsuyum.restaurant.domain.model.Category;
import com.onsuyum.restaurant.domain.repository.CategoryRepository;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse findOrCreateCategory(String name) {
        return findEntityOrCreateCategory(name).toResponseDTO();
    }

    @Transactional
    public List<CategoryResponse> findOrCreateCategories(Set<String> categoryNames) {
        List<Category> categories = findEntityOrCreateCategories(categoryNames);

        return categories.stream()
                .map(Category::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse findById(Long id) {
        Category category = findEntityById(id);

        return category.toResponseDTO();
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> findAll(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAllWithRestaurantCount(pageable);
        return categories.map(Category::toResponseDTO);
    }

    @Transactional
    public void deleteById(Long id) {
        Category category = findEntityById(id);
        categoryRepository.delete(category);
    }


    // Service Layer 내에서 사용 가능한 메서드

    @Transactional(readOnly = true)
    Category findEntityById(Long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

    @Transactional
    public Category findEntityOrCreateCategory(String name) {
        return categoryRepository.findByName(name)
                .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                                .name(name)
                                .build()
                        )
                );
    }

    @Transactional
    public List<Category> findEntityOrCreateCategories(Set<String> categoryNames) {
        return categoryNames.stream()
                .map(this::findEntityOrCreateCategory)
                .collect(Collectors.toList());
    }
}
