package com.onsuyum.security.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;

    @Builder
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
