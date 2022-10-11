package com.onsuyum.security.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "토큰 Response")
public class TokenResponse {

    @Schema(description = "jwt 엑세스 토큰")
    private String accessToken;

    @Builder
    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
