package com.onsuyum.babful.controller;

import com.onsuyum.babful.domain.service.BabfulMenuService;
import com.onsuyum.babful.dto.response.BabfulMenuResponse;
import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.SuccessResponseBody;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/babfuls")
@Api(tags = "Babful Menu API")
public class BabfulMenuController {

    private final BabfulMenuService babfulMenuService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "밥풀 식단 메뉴들 조회",
            description =
                    "밥풀 식단 메뉴들을 조회합니다. 기본적으로 parameter isOldMenu의 default는 0이며 오늘을 포함한 다음날부터의 식단을 보여줍니다.\n"
                            +
                            "isOldMenu가 1이면 과거 식단을 불러옵니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "밥풀 식단 메뉴들 조회 성공"),
                    @ApiResponse(responseCode = "204", description = "밥풀 식단 메뉴들 없음")
            }
    )
    public ResponseEntity<SuccessResponseBody<Page<BabfulMenuResponse>>> findAllFutureMenus(
            @PageableDefault(sort = "menuDate", direction = Sort.Direction.ASC, size = 20) Pageable pageable,
            @RequestParam(defaultValue = "0") @Parameter(description = "과거 메뉴 불러오기 유무(default value = 0)") boolean isOldMenu) {

        Page<BabfulMenuResponse> page = babfulMenuService.findAll(pageable, isOldMenu);

        if (page.isEmpty()) {
            return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.NO_CONTENT_MENUS);
        }

        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_GET_MENUS, page);
    }
}
