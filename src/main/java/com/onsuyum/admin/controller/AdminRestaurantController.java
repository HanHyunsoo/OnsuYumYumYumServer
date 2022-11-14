package com.onsuyum.admin.controller;

import com.onsuyum.admin.dto.request.AdminJsonRestaurantRequest;
import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.restaurant.domain.service.RestaurantService;
import com.onsuyum.restaurant.dto.request.MultipartRestaurantRequest;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/")
@Api(tags = "Admin Restaurant API")
public class AdminRestaurantController {

    private final RestaurantService restaurantService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/restaurants", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "음식점 저장",
            description = "음식점을 저장합니다. 파라미터로 isRequest를 받으며 default가 false입니다.\n" +
                    "익명 사용자에게 음식점을 보이게 하려면 false로 설정해주세요",
            responses = {
                    @ApiResponse(responseCode = "201", description = "음식점 저장 성공"),
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> save(
            @Parameter(description = "음식점 제안 여부") @RequestParam(defaultValue = "false", required = false) boolean isRequest,
            @Parameter(description = "음식점 request", schema = @Schema(type = "object")) @ModelAttribute MultipartRestaurantRequest multipartRestaurantRequest) {
        RestaurantResponse restaurantResponse = restaurantService.save(multipartRestaurantRequest,
                isRequest);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_CREATE_MENUS,
                        restaurantResponse
                );
    }

    @PostMapping(path = "/restaurants/{id}/outside-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점 외부 이미지 저장",
            description = "해당 ID 음식점의 외부 이미지를 저장합니다. 이미 그 음식점에 외부 이미지가 존재한다면 저장 할 수 없습니다.\n" +
                    "만약 이미지의 수정을 원한다면 삭제 후 이 URI를 요청해주세요",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 외부 저장 성공"),
                    @ApiResponse(responseCode = "400", description = "해당 ID 음식점의 외부 이미지가 이미 존재합니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> saveOutsideImage(
            @Parameter(description = "음식점 id") @PathVariable Long id,
            @Parameter(description = "새로 부여할 외부 이미지 파일") @RequestPart MultipartFile image) {
        RestaurantResponse restaurantResponse = restaurantService.saveOutsideImage(id, image);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_UPDATE_RESTAURANT_OUTSIDE_IMAGE,
                        restaurantResponse
                );
    }

    @PostMapping(path = "/restaurants/{id}/inside-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점 내부 이미지 저장",
            description = "해당 ID 음식점의 내부 이미지를 저장합니다. 이미 그 음식점에 내부 이미지가 존재한다면 저장 할 수 없습니다.\n" +
                    "만약 이미지의 수정을 원한다면 삭제 후 이 URI를 요청해주세요",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 내부 저장 성공"),
                    @ApiResponse(responseCode = "400", description = "해당 ID 음식점의 내부 이미지가 이미 존재합니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 음식점 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> saveInsideImage(
            @Parameter(description = "음식점 id") @PathVariable Long id,
            @Parameter(description = "새로 부여할 내부 이미지 파일") @RequestPart MultipartFile image) {
        RestaurantResponse restaurantResponse = restaurantService.saveInsideImage(id, image);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_UPDATE_RESTAURANT_INSIDE_IMAGE,
                        restaurantResponse
                );
    }

    @GetMapping(path = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "모든 음식점 불러오기",
            description = "음식점을 불러옵니다. 파라미터는 keyword, isRequest가 있습니다.\n" +
                    "keyword로 음식점 이름을 매칭해 검색하고 필수는 아닙니다.\n" +
                    "isRequest는 익명 사용자에게 보이는 여부를 지정하고 default로 false고 익명사용자에게 보입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 불러오기 성공"),
                    @ApiResponse(responseCode = "204", description = "음식점 데이터 없음")
            }
    )
    public ResponseEntity<SuccessResponseBody<Page<RestaurantResponse>>> findAll(
            @Parameter(description = "pageable") Pageable pageable,
            @Parameter(description = "음식점 제안 여부") @RequestParam(defaultValue = "false", required = false) boolean isRequest,
            @Parameter(description = "음식점의 이름 키워드") @RequestParam(name = "keyword", defaultValue = "", required = false) String name) {
        Page<RestaurantResponse> page;
        if (name.isBlank()) {
            page = restaurantService.findAllByRequest(pageable, isRequest);
        } else {
            page = restaurantService.findAllByNameAndRequest(pageable, name, isRequest);
        }

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RESTAURANTS,
                        page
                );
    }

    @GetMapping(path = "/restaurants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점 불러오기",
            description = "해당 ID의 음식점을 불러옵니다. ID로 존재하는 음식점이 없다면 404를 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 불러오기 성공"),
                    @ApiResponse(responseCode = "404", description = "음식점 데이터 존재하지 않음")
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> findById(
            @Parameter(description = "음식점 ID") @PathVariable Long id) {
        RestaurantResponse restaurantResponse = restaurantService.findById(id);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_GET_RESTAURANT,
                        restaurantResponse
                );
    }

    @PatchMapping(path = "/restaurants/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점 수정",
            description = "음식점을 수정합니다. 이미지 파일을 제외한 필드들을 request body로 받아와 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 수정 성공"),
                    @ApiResponse(responseCode = "404", description = "음식점 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class)))
            }
    )
    public ResponseEntity<SuccessResponseBody<RestaurantResponse>> updateById(
            @Parameter(description = "음식점 ID") @PathVariable Long id,
            @RequestBody AdminJsonRestaurantRequest request) {

        RestaurantResponse restaurantResponse = restaurantService.update(id, request,
                request.isRequest());

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_UPDATE_MENU,
                        restaurantResponse
                );
    }

    @DeleteMapping(path = "/restaurants/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점 삭제",
            description = "음식점을 삭제합니다",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "음식점 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class)))
            }
    )
    public ResponseEntity<SuccessResponseBody<Void>> deleteById(
            @Parameter(description = "음식점 ID") @PathVariable Long id) {
        restaurantService.deleteById(id);

        return SuccessResponseBody
                .toResponseEntity(
                        StatusEnum.SUCCESS_DELETE_RESTAURANT,
                        null
                );
    }

    @DeleteMapping(path = "/restaurants/{id}/outside-image", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 외부 이미지 삭제",
            description = "음식점 ID로 해당 음식점의 외부 이미지를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 외부 이미지 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID 음식점은 존재하지 않음 or 해당 ID 음식점의 외부 이미지는 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }

    )
    public ResponseEntity<SuccessResponseBody<Void>> deleteOutsideImageById(
            @Parameter(description = "음식점 ID") @PathVariable Long id) {
        restaurantService.deleteOutsideImageById(id);

        return SuccessResponseBody.toEmptyResponseEntity(
                StatusEnum.SUCCESS_DELETE_RESTAURANT_OUTSIDE_IMAGE);
    }

    @DeleteMapping(path = "/restaurants/{id}/inside-image", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "해당 ID 음식점의 내부 이미지 삭제",
            description = "음식점 ID로 해당 음식점의 내부 이미지를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "음식점 내부 이미지 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID 음식점은 존재하지 않음 or 해당 ID 음식점의 내부 이미지는 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }

    )
    public ResponseEntity<SuccessResponseBody<Void>> deleteMenuImageById(
            @PathVariable @Parameter(description = "Menu Id") Long id) {
        restaurantService.deleteInsideImageById(id);

        return SuccessResponseBody.toEmptyResponseEntity(
                StatusEnum.SUCCESS_DELETE_RESTAURANT_INSIDE_IMAGE);
    }
}
