package org.cachewrapper.token.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.cachewrapper.token.filter.domain.SessionAuthentication;
import org.cachewrapper.token.repository.RefreshTokenRepository;
import org.cachewrapper.token.service.token.AccessTokenService;
import org.cachewrapper.token.service.token.RefreshTokenService;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> PUBLIC_PATH_LIST = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/auth/token/refresh"
    );

    private final AccessTokenService accessTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

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

        var cookieArray = httpServletRequest.getCookies();
        if (cookieArray == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var cookies = Arrays.asList(cookieArray);
        var refreshTokenUUIDCookieOptional = cookies.stream()
                .filter(cookie -> cookie.getName().equals("refresh_token_uuid"))
                .findFirst();

        var refreshTokenUUIDCookie = refreshTokenUUIDCookieOptional.orElse(null);
        if (refreshTokenUUIDCookie == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var refreshTokenUUIDString = refreshTokenUUIDCookie.getValue();
        var refreshTokenUUID = UUID.fromString(refreshTokenUUIDString);
        var refreshTokenEntity = refreshTokenRepository.findById(refreshTokenUUID).orElse(null);
        if (refreshTokenEntity == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var accessTokenCookieOptional = cookies.stream()
                .filter(cookie -> cookie.getName().equals("access_token"))
                .findFirst();
        var accessTokenCookie = accessTokenCookieOptional.orElse(null);
        if (accessTokenCookie == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var accessTokenString = accessTokenCookie.getValue();
        if (!accessTokenService.validateTokenString(accessTokenString)) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var refreshTokenString = refreshTokenEntity.getRefreshTokenString();
        var accessToken = accessTokenService.getTokenFromString(accessTokenString);
        var userUUID = accessToken.userUUID();
        var username = accessToken.username();

        var authentication = new SessionAuthentication(userUUID, username, accessTokenCookie.getValue(), refreshTokenString);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}