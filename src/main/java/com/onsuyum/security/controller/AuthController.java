package com.onsuyum.security.controller;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.common.response.FailureResponseBody;
import com.onsuyum.common.response.SuccessResponseBody;
import com.onsuyum.security.domain.service.AuthService;
import com.onsuyum.security.dto.request.LoginRequest;
import com.onsuyum.security.dto.response.TokenResponse;
import com.onsuyum.security.dto.response.UserResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = "Auth API")
public class AuthController {

    private final AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "로그인",
            description = "아이디와 비밀번호를 입력받아서 DB에 존재하는 USER와 일치한다면 인증, 인가에 필요한 jwt 토큰을 생성해 반환합니다.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "로그인 성공, access token 발급 성공"),
                    @ApiResponse(responseCode = "401", description = "로그인 실패(아이디는 존재, 비밀번호 틀림)", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "404", description = "로그인 실패(아이디로 존재하는 User가 없음)", content = @Content(schema = @Schema(implementation = FailureResponseBody.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
            }
    )
    public ResponseEntity<SuccessResponseBody<TokenResponse>> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);

        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_LOGIN, tokenResponse);
    }

    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "로그아웃",
            description = "request로 받은 access token과 refresh token을(refresh는 추후 추가 예정) 제거합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            }
    )
    public ResponseEntity<SuccessResponseBody<Object>> logout(HttpServletResponse response) {
        authService.logout(response);

        return SuccessResponseBody.toEmptyResponseEntity(StatusEnum.SUCCESS_LOGOUT);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(
            summary = "내 정보 확인",
            description = "현재 로그인 된 유저의 정보를 반환 합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "내 정보 반환 성공"),
                    @ApiResponse(responseCode = "403", description = "내 정보 반환 실패")
            }
    )
    public ResponseEntity<SuccessResponseBody<UserResponse>> info(@ApiIgnore @AuthenticationPrincipal UserDetails userDetails) {
        return SuccessResponseBody.toResponseEntity(StatusEnum.SUCCESS_GET_USER_INFO, authService.getInfo(userDetails));
    }
}
