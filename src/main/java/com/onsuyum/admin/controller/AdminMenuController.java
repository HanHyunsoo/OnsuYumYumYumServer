package com.onsuyum.admin.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.MenuService;
import com.onsuyum.restaurant.dto.request.JsonMenuRequest;
import com.onsuyum.restaurant.dto.request.MultipartMenuRequestList;
import com.onsuyum.restaurant.dto.response.MenuResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/")
@Api(tags = "Admin Menu API")
public class AdminMenuController {

    private final MenuService menuService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/restaurants/{id}/menus", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 메뉴들 전체 저장",
            description = "여러개의 메뉴 Request를 받아서 해당하는 음식점 ID에 관계를 매핑해 DB에 저장합니다.\n" +
                    "요청자는 음식점에 대해 접근할 수 있는 권한이 있어야 메뉴들을 성공적으로 저장할 수 있습니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "메뉴들 저장 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<List<MenuResponse>>> saveAll(@PathVariable @Parameter(description = "음식점 ID") Long id,
                                                                           @Parameter(description = "메뉴 requests", schema = @Schema(type = "object")) @ModelAttribute MultipartMenuRequestList multipartMenuRequestList) {
        List<MenuResponse> menuResponses = menuService.saveAll(id, multipartMenuRequestList.getMenuRequestList());

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_CREATE_MENUS,
                        menuResponses
                );
    }

    @GetMapping(path = "/restaurants/{id}/menus", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 메뉴들 전체 조회",
            description = "음식점 ID에 해당하는 모든 메뉴 정보를 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "메뉴들 저장 성공"),
                    @ApiResponse(responseCode = "204", description = "메뉴들 없음"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점이 존재하지 않습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<List<MenuResponse>>> findAllByRestaurantId(@PathVariable @Parameter(description = "음식점 ID") Long id) {
        List<MenuResponse> menuResponses = menuService.findAllByRestaurantIdWithRequest(id, false);

        if (menuResponses.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_MENUS);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_MENUS,
                        menuResponses
                );
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/menus/{id}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 메뉴 이미지 저장",
            description = "해당 ID 메뉴의 이미지를 저장합니다. 이미 그 메뉴에 이미지가 존재한다면 저장 할 수 없습니다.\n" +
                    "만약 이미지의 수정을 원한다면 삭제 후 이 URI를 요청해주세요",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메뉴 이미지 저장 성공"),
                    @ApiResponse(responseCode = "400", description = "해당 ID 메뉴의 이미지가 이미 존재합니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 메뉴 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<SuccessResponseBody<MenuResponse>> saveMenuImageById(@PathVariable @Parameter(description = "메뉴 Id") Long id,
                                                                                     @Parameter(description = "저장할 메뉴 이미지") @RequestPart MultipartFile imageFile) {
        MenuResponse menuResponse = menuService.saveMenuImageById(id, imageFile);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_UPDATE_MENU_IMAGE,
                        menuResponse
                );
    }

    @GetMapping(path = "/menus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID의 메뉴 조회",
            description = "메뉴 ID로 해당 메뉴를 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메뉴 조회 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 메뉴 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }

    )
    public ResponseEntity<SuccessResponseBody<MenuResponse>> findById(@PathVariable @Parameter(description = "Menu Id") Long id) {
        MenuResponse menuResponse = menuService.findById(id);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_MENU,
                        menuResponse
                );
    }

    // "multipart/form-data"는 Patch를 지원하지 않는다.
    @PatchMapping(path = "/menus/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID의 메뉴 수정",
            description = "메뉴 ID로 해당 메뉴를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메뉴 수정 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 메뉴 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }

    )
    public ResponseEntity<SuccessResponseBody<MenuResponse>> updateById(
            @Parameter(description = "메뉴 id") @PathVariable Long id,
            @RequestBody JsonMenuRequest menuRequest) {

        MenuResponse menuResponse = menuService.update(id, menuRequest);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_UPDATE_MENU,
                        menuResponse
                );
    }

    @DeleteMapping(path = "/menus/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID의 메뉴 삭제",
            description = "메뉴 ID로 해당 메뉴를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메뉴 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 메뉴 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }

    )
    public ResponseEntity<SuccessResponseBody<Void>> deleteById(@PathVariable @Parameter(description = "Menu Id") Long id) {
        menuService.deleteById(id);

        return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.SUCCESS_DELETE_MENU);
    }

    @DeleteMapping(path = "/menus/{id}/images", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 메뉴의 이미지 삭제",
            description = "메뉴 ID로 해당 메뉴의 이미지를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "메뉴 이미지 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 이미지는 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }

    )
    public ResponseEntity<SuccessResponseBody<Void>> deleteMenuImageById(@PathVariable @Parameter(description = "Menu Id") Long id) {
        menuService.deleteMenuImageById(id);

        return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.SUCCESS_DELETE_MENU_IMAGE);
    }
}
