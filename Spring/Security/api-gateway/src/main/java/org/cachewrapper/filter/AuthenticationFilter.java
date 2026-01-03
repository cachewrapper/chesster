package org.cachewrapper.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.cachewrapper.token.service.AccessTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_PATH_LIST = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/auth/token/refresh"
    );

    protected final AccessTokenService accessTokenService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            @NotNull HttpServletRequest httpServletRequest,
            @NotNull HttpServletResponse httpServletResponse,
            @NotNull FilterChain filterChain
    ) {
        var requestPath = httpServletRequest.getRequestURI();
        var isPublicPath = PUBLIC_PATH_LIST.stream().anyMatch(requestPath::endsWith);
        if (isPublicPath) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var cookieList = Arrays.asList(cookies);
        if (cookieList.isEmpty()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var accessTokenCookieOptional = cookieList.stream()
                .filter(cookie -> cookie.getName().equals("access_token"))
                .findFirst();
        if (accessTokenCookieOptional.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var accessTokenCookie = accessTokenCookieOptional.get();
        if (!accessTokenService.validateTokenString(accessTokenCookie.getValue())) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var accessToken = accessTokenService.getTokenFromString(accessTokenCookie.getValue());
        var userUUID = accessToken.userUUID();

        var authenticationToken = new UsernamePasswordAuthenticationToken(userUUID.toString(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}