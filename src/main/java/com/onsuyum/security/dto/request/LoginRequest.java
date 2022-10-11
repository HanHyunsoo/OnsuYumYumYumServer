package com.onsuyum.security.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "로그인 정보 Request")
public class LoginRequest {

    @Schema(description = "유저 아이디", example = "gustn8523")
    private String username;
    @Schema(description = "유저 비밀번호", example = "1q2w3e4r!!")
    private String password;

    @Builder
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
