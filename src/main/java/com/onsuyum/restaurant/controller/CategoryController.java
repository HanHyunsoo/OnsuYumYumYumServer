package com.onsuyum.restaurant.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.CategoryService;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<SuccessResponseBody<Page<CategoryResponse>>> findAll(Pageable pageable) {
        Page<CategoryResponse> categoryResponsePage = categoryService.findAll(pageable);

        if (categoryResponsePage.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_CATEGORIES);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_CATEGORIES,
                        categoryResponsePage
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseBody<CategoryResponse>> findById(@PathVariable Long id) {
        CategoryResponse categoryResponse = categoryService.findById(id);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_CATEGORY,
                        categoryResponse
                );
    }
}
