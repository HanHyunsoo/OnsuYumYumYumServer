package com.onsuyum.restaurant.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.RestaurantCategoryService;
import com.onsuyum.restaurant.dto.response.CategoryResponse;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Api(tags = "Restaurant Category API")
public class RestaurantCategoryController {

    private final RestaurantCategoryService restaurantCategoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/restaurants/{id}/categories", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 카테고리 생성",
            description =
                    "여러개의 카테고리를 request로 받아서 카테고리를 찾고 없으면 생성을 한다음 그 카테고리들을 해당 ID 음식점의 연관관계를 설정하고 저장합니다\n"
                            +
                            "요청자는 음식점에 대해 접근할 수 있는 권한이 있어야 메뉴들을 성공적으로 저장할 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "카테고리들 해당 ID의 음식점 관계 저장 성공"),
                    @ApiResponse(responseCode = "403", description = "해당 ID의 음식점을 접근할 시간이 지났거나 권한이 없습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<Map<String, Object>>> saveAllRestaurantCategoryById(
            @PathVariable @Parameter(description = "음식점 ID") Long id,
            @RequestBody Set<String> categoryRequest) {
        if (categoryRequest.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "카테고리를 한개 이상 요청 보내야 합니다.");
        }

        Map<String, Object> responses = restaurantCategoryService.saveAllRestaurantCategoryWithRequest(
                id, categoryRequest, true);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_CREATE_CATEGORIES_BY_RESTAURANT,
                        responses
                );
    }

    @GetMapping(path = "/restaurants/{id}/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 카테고리들 조회",
            description = "해당 ID 음식점의 카테고리 관계를 조회한다음 해당하는 카테고리들을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "해당 ID 음식점의 카테고리들 불러오기 성공"),
                    @ApiResponse(responseCode = "204", description = "해당 ID 음식점의 카테고리 없음"),
                    @ApiResponse(responseCode = "403", description = "해당 ID의 음식점 권한이 없습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<Page<CategoryResponse>>> findAllCategoryByRestaurantId(
            @PathVariable @Parameter(description = "음식점 ID") Long id, Pageable pageable) {
        Page<CategoryResponse> categoryResponsePage = restaurantCategoryService.findAllCategoryByRestaurantIdWithRequest(
                pageable, id, true);

        if (categoryResponsePage.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_RESTAURANTS);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RESTAURANTS,
                        categoryResponsePage
                );
    }

    @GetMapping(path = "/categories/{id}/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 카테고리의 음식점들 조회",
            description = "해당 ID 카테고리의 음식점 관계를 조회한다음 해당하는 음식점들을 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "해당 ID 카테고리의 음식점들 불러오기 성공"),
                    @ApiResponse(responseCode = "204", description = "해당 ID 카테고리의 음식점 없음"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 카테고리가 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<Page<RestaurantResponse>>> findAllRestaurantByCategoryId(
            @PathVariable @Parameter(description = "카테고리 ID") Long id, Pageable pageable) {
        Page<RestaurantResponse> restaurantResponsePage = restaurantCategoryService.findAllRestaurantByCategoryIdWithRequest(
                pageable, id);

        if (restaurantResponsePage.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_CATEGORIES);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_CATEGORIES,
                        restaurantResponsePage
                );
    }
}
