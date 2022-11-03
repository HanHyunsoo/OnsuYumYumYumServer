package com.onsuyum.restaurant.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.CategoryService;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@Api(tags = "Category API")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "모든 카테고리 조회",
            description = "DB에 존재하는 모든 카테고리를 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "모든 카테고리 조회 성공"),
                    @ApiResponse(responseCode = "204", description = "카테고리 없음")
            }
    )
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

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "특정 카테고리 조회",
            description = "해당 ID를 가진 카테고리를 DB에 검색후 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "카테고리 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "카테고리 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<SuccessResponseBody<CategoryResponse>> findById(
            @PathVariable @Parameter(description = "카테고리 ID") Long id) {
        CategoryResponse categoryResponse = categoryService.findById(id);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_CATEGORY,
                        categoryResponse
                );
    }
}
