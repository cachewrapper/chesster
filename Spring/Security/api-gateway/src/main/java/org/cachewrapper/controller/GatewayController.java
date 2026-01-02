package org.cachewrapper.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class GatewayController {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Map<String, String> SERVICE_MAP = Map.of(
            "auth", "http://localhost:8081",
            "users", "http://localhost:8082",
            "game", "http://localhost:8083"
    );

    @RequestMapping("/{service}/**")
    @SneakyThrows
    public void proxyService(
            @PathVariable("service") String service,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String baseUrl = SERVICE_MAP.get(service);
        System.out.println(baseUrl);
        if (baseUrl == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }

        String path = request.getRequestURI().substring(request.getContextPath().length());
        String targetUrl = baseUrl + path + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
        System.out.println(targetUrl);

        var httpHeaders = new HttpHeaders();
        Collections.list(request.getHeaderNames()).forEach(name -> httpHeaders.set(name, request.getHeader(name)));
        addCookiesToHeaders(request, httpHeaders);

        var requestBody = request.getInputStream();
        var bodyByteArray = StreamUtils.copyToByteArray(requestBody);

        var httpMethod = HttpMethod.valueOf(request.getMethod());
        var httpEntity = new HttpEntity<>(bodyByteArray, httpHeaders);

        var serviceResponse = restTemplate.exchange(targetUrl, httpMethod, httpEntity, byte[].class);
        response.setStatus(serviceResponse.getStatusCode().value());
        serviceResponse
                .getHeaders()
                .forEach((key, values) ->
                        values.forEach(value -> response.addHeader(key, value))
                );

        var outputStream = response.getOutputStream();
        StreamUtils.copy(Objects.requireNonNull(serviceResponse.getBody()), outputStream);
        outputStream.flush();
    }

    private void addCookiesToHeaders(HttpServletRequest request, HttpHeaders headers) {
        var cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return;

        StringBuilder sb = new StringBuilder();
        for (var cookie : cookies) {
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append("; ");
        }
        if (sb.length() > 2) sb.setLength(sb.length() - 2);
        headers.add(HttpHeaders.COOKIE, sb.toString());
    }
}