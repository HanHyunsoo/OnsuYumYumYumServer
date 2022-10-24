package com.onsuyum.restaurant.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.MenuService;
import com.onsuyum.restaurant.dto.request.ModelAttributeMenuRequest;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import com.onsuyum.restaurant.dto.response.RestaurantMenuResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
@Api(tags = "Menu API")
public class MenuController {

    private final MenuService menuService;

    // TODO 스웨거에서 modelattribute에서 list가 제대로 안불러와짐.. 해결을 어떻게할까..
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/{id}/menus", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 메뉴들 전체 저장",
            description = "여러개의 메뉴 Request를 받아서 해당하는 음식점 ID에 관계를 매핑해 DB에 저장합니다.\n" +
                    "요청자는 음식점에 대해 접근할 수 있는 권한이 있어야 메뉴들을 성공적으로 저장할 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "메뉴들 저장 성공"),
                    @ApiResponse(responseCode = "403", description = "해당 ID의 음식점을 접근할 시간이 지났거나 권한이 없습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<List<MenuResponse>>> saveAll(@PathVariable @Parameter(description = "음식점 ID") Long id,
                                                                           @Parameter(description = "메뉴 requests", schema = @Schema(type = "object")) @ModelAttribute ModelAttributeMenuRequest modelAttributeMenuRequest) {
        List<MenuResponse> menuResponses = menuService.saveAllWithRequest(id, modelAttributeMenuRequest.getMenuRequestList(), true);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_CREATE_MENUS,
                        menuResponses
                );
    }

    @GetMapping(path = "/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "모든 메뉴들 전체 조회",
            description = "모든 메뉴들을 조회합니다. param에서 price의 값을 지정하면 해당 가격 이상의 메뉴만 보입니다. 기본적으로 sorting은 price asc로 되어있습니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메뉴들 불러오기 성공"),
                    @ApiResponse(responseCode = "204", description = "메뉴들 정보 없음")
            }
    )
    public ResponseEntity<SuccessResponseBody<Page<RestaurantMenuResponse>>> findAll(
            @PageableDefault(sort = "price", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(defaultValue = "0", required = false) @Parameter(description = "메뉴 가격")  Integer price) {
        Page<RestaurantMenuResponse> restaurantMenuResponses = menuService.findAllWithRequest(pageable, false, price);

        if (restaurantMenuResponses.isEmpty()) {
            SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_MENUS);
        }

        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_GET_MENUS, restaurantMenuResponses);
    }

    @GetMapping(path = "/{id}/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 메뉴들 전체 조회",
            description = "음식점 ID에 해당하는 모든 메뉴 정보를 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "메뉴들 저장 성공"),
                    @ApiResponse(responseCode = "204", description = "메뉴들 없음"),
                    @ApiResponse(responseCode = "403", description = "해당 ID의 음식점의 권한이 없습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<List<MenuResponse>>> findAllByRestaurantId(@PathVariable @Parameter(description = "음식점 ID") Long id) {
        List<MenuResponse> menuResponses = menuService.findAllByRestaurantIdWithRequest(id, true);

        if (menuResponses.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_MENUS);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_MENUS,
                        menuResponses
                );
    }

}
