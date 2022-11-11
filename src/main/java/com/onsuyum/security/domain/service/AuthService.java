package com.onsuyum.security.domain.service;

import com.onsuyum.common.exception.LoginFailException;
import com.onsuyum.security.domain.model.User;
import com.onsuyum.security.dto.request.LoginRequest;
import com.onsuyum.security.dto.response.TokenResponse;
import com.onsuyum.security.dto.response.UserResponse;
import com.onsuyum.security.token.JwtTokenProvider;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final CustomUserDetailService customUserDetailService;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest loginRequest) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(
                loginRequest.getUsername());

        if (!passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())) {
            throw new LoginFailException();
        }

        List<String> roles = toStringList(userDetails.getAuthorities());

        return TokenResponse.builder()
                            .accessToken(
                                    jwtTokenProvider.createToken(userDetails.getUsername(), roles))
                            .build();
    }

    @Transactional(readOnly = true)
    public UserResponse getInfo(UserDetails userDetails) {
        User user = (User) userDetails;
        return user.toResponseDTO();
    }

    public void logout(HttpServletResponse response) {
        response.setHeader("Authorization", "");
    }

    private List<String> toStringList(Collection<? extends GrantedAuthority> roles) {
        return roles.stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
    }
}
