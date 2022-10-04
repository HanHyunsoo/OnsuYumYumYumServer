package com.onsuyum.security.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UserResponse {
    private String username;
    private List<String> roles;

    @Builder
    public UserResponse(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }
}
