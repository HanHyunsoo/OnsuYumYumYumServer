package com.onsuyum.admin.controller;

import com.onsuyum.babful.domain.service.BabfulMenuService;
import com.onsuyum.babful.dto.request.BabfulMenuRequest;
import com.onsuyum.babful.dto.response.BabfulMenuResponse;
import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/")
@Api(tags = "Admin Babful Menu API")
public class AdminBabfulMenuController {

    private final BabfulMenuService babfulMenuService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/babfuls", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "밥풀 메뉴들 전체 저장",
            description = "여러개의 밥풀 메뉴 Request를 받아서 저장합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "밥풀 메뉴들 저장 성공"),
                    @ApiResponse(responseCode = "400", description = "request 중 menuDate가 중복되는 값이 있습니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<List<BabfulMenuResponse>>> create(
            @RequestBody List<BabfulMenuRequest> requests) {
        List<BabfulMenuResponse> responses = babfulMenuService.saveAll(requests);

        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_CREATE_MENUS, responses);
    }

    @GetMapping(value = "/babfuls", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "밥풀 메뉴들 전체 불러오기",
            description = "여러개의 밥풀 메뉴를 불러옵니다. isOldData를 파라미터로 받으며 해당 값이 없으면 날짜 상관없이 모두 불러옵니다.\n"
                    + "만약 isOldData가 true면 오늘날짜 기준으로 과거의 데이터를 불러오고 그 반대면 오늘 포함 미래의 데이터를 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "밥풀 메뉴들 불러오기 성공"),
                    @ApiResponse(responseCode = "204", description = "밥풀 메뉴들 없음"),
            }
    )
    public ResponseEntity<SuccessResponseBody<Page<BabfulMenuResponse>>> findAll(
            @Parameter(description = "과거 데이터 확인 유무") @RequestParam(required = false) Boolean isOldData,
            @PageableDefault(sort = "menuDate", direction = Direction.DESC) Pageable pageable) {
        Page<BabfulMenuResponse> responses;
        if (isOldData == null) {
            responses = babfulMenuService.findAll(pageable);
        } else {
            responses = babfulMenuService.findAll(pageable, isOldData);
        }

        if (responses.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_MENUS);
        }

        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_GET_MENUS, responses);
    }

    @GetMapping(value = "/babfuls/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "밥풀 메뉴 ID로 불러오기",
            description = "해당 ID의 밥풀 메뉴를 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "밥풀 메뉴 불러오기 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 밥풀 메뉴 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<BabfulMenuResponse>> findById(@Parameter(description = "밥풀 메뉴 ID") @PathVariable Long id) {
        BabfulMenuResponse response = babfulMenuService.findById(id);

        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_GET_MENU, response);
    }

    @PatchMapping(value = "/babfuls/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "밥풀 메뉴 ID로 수정하기",
            description = "해당 ID의 밥풀 메뉴를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "밥풀 메뉴 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "menuDate가 이미 DB에 존재합니다.", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 밥풀 메뉴 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<BabfulMenuResponse>> updateById(
            @Parameter(description = "밥풀 메뉴 ID") @PathVariable Long id,
            @RequestBody BabfulMenuRequest request) {
        BabfulMenuResponse response = babfulMenuService.updateById(id, request);

        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_UPDATE_MENU, response);
    }

    @DeleteMapping(value = "/babfuls/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "밥풀 메뉴 ID로 삭제하기",
            description = "해당 ID의 밥풀 메뉴를 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "밥풀 메뉴 삭제 성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 밥풀 메뉴 존재하지 않음", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
            }
    )
    public ResponseEntity<SuccessResponseBody<BabfulMenuResponse>> deleteById(@Parameter(description = "밥풀 메뉴 ID") @PathVariable Long id) {
        babfulMenuService.deleteById(id);

        return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.SUCCESS_DELETE_MENU);
    }
}
