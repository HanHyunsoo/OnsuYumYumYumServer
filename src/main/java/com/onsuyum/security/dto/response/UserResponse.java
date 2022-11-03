package com.onsuyum.security.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "유저 정보 Response")
public class UserResponse {

    @Schema(description = "현재 로그인 된 유저 아이디", example = "gustn8523")
    private String username;
    @Schema(description = "현재 로그인 한 유저의 roles")
    private List<String> roles;

    @Builder
    public UserResponse(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }
}
