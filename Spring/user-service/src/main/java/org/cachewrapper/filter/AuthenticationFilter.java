package org.cachewrapper.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.cachewrapper.token.service.token.AccessTokenService;
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

    protected final AccessTokenService accessTokenService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            @NotNull HttpServletRequest httpServletRequest,
            @NotNull HttpServletResponse httpServletResponse,
            @NotNull FilterChain filterChain
    ) {
        var cookies = Arrays.asList(httpServletRequest.getCookies());
        if (cookies.isEmpty()) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var accessTokenCookieOptional = cookies.stream()
                .filter(cookie -> cookie.getName().equals("access_token"))
                .findFirst();
        if (accessTokenCookieOptional.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        var accessTokenCookie = accessTokenCookieOptional.get();
        var accessToken = accessTokenService.getTokenFromString(accessTokenCookie.getValue());
        var userUUID = accessToken.userUUID();

        var authenticationToken = new UsernamePasswordAuthenticationToken(userUUID.toString(), null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}