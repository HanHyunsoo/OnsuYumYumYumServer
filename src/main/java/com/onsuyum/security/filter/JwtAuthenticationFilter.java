package com.onsuyum.security.filter;

import com.onsuyum.security.token.JwtTokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext()
                                 .setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
