package com.onsuyum.restaurant.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.RestaurantService;
import com.onsuyum.restaurant.dto.request.RestaurantRequest;
import com.onsuyum.restaurant.dto.response.RestaurantResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@Api(tags = "Restaurant API")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "음식점 저장",
            description = "음식점 Request를 받아서 DB에 저장합니다. 익명 유저도 요청할 수 있는 대신 생성된 정보를 어드민이 승인해야 비로소 다른 유저들이 접근 할 수 있습니다.\n" +
                    "클라이언트 쪽에서 음식점 제보하기 기능을 만든다면 메뉴들을 저장할 uri(post, /api/restaurants)를 먼저 요청해서 id를 받은 다음 카테고리(post, /api/restaurants/{id}/categories), 메뉴(post, /api/restaurants/{id}/menus)를 마저 저장하는 것을 권장합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "음식점 저장 성공")
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> saveRestaurantWithRequest(@ModelAttribute RestaurantRequest dto) {
        RestaurantResponse restaurantResponse = restaurantService.save(dto, true);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_CREATE_RESTAURANT,
                        restaurantResponse
                );
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "모든 음식점 조회",
            description = "제안으로 저장된 음식점들(익명 유저가 접근 할 수 없는)을 제외한 모든 음식점을 불러옵니다.\n" +
                    "키워드 파라미터를 이용하면 검색용도로도 사용 할 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점들 조회 성공"),
                    @ApiResponse(responseCode = "204", description = "음식점들 정보 없음")
            }
    )
    public ResponseEntity<SuccessResponseBody<Page<RestaurantResponse>>> findAllRestaurantWithNotRequest(Pageable pageable,
                                                                                                         @RequestParam(name = "keyword", required = false) @Parameter(description = "음식점의 이름 키워드") String name) {
        Page<RestaurantResponse> restaurantResponsePage;
        if (name.isBlank()) {
            restaurantResponsePage = restaurantService.findAllByRequest(pageable, false);
        } else {
            restaurantResponsePage = restaurantService.findAllByNameAndRequest(pageable, name, false);
        }

        if (restaurantResponsePage.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_RESTAURANTS);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RESTAURANTS,
                        restaurantResponsePage
                );
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "헤당 ID 음식점 조회",
            description = "ID로 DB에 존재하는 음식점을 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "음식점 정보 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> findRestaurantWithNotRequest(@PathVariable @Parameter(description = "음식점 ID") Long id) {
        RestaurantResponse restaurantResponse = restaurantService.findByIdWithRequest(id, true);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RESTAURANT,
                        restaurantResponse
                );
    }

    @GetMapping("/random")
    @Operation(
            summary = "랜덤 음식점 조회",
            description = "익명 유저가 접근할 수 있는 음식점 중 한개를 임의로 불어옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "음식점 정보 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> findRandomRestaurantWithNotRequest() {
        RestaurantResponse restaurantResponse = restaurantService.findRandomRestaurant();

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RANDOM_RESTAURANT,
                        restaurantResponse
                );
    }
}