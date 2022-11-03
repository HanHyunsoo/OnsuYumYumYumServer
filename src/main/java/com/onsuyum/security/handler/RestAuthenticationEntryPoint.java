package com.onsuyum.security.handler;

import com.onsuyum.common.StatusEnum;
import com.onsuyum.security.token.JwtTokenProvider;
import com.onsuyum.security.util.HeaderUtil;
import com.onsuyum.security.util.ResponseUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        String tokenString = HeaderUtil.getAccessToken(request);

        if (tokenString == null) {
            ResponseUtil.setResponse(response, StatusEnum.ACCESS_TOKEN_IS_NULL);
            return;
        }

        if (!jwtTokenProvider.validateToken(tokenString)) {
            ResponseUtil.setResponse(response, StatusEnum.INVALID_OR_EXPIRE_ACCESS_TOKEN);
        }
    }
}
