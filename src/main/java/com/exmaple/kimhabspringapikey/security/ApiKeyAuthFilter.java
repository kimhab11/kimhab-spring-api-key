package com.exmaple.kimhabspringapikey.security;

import com.exmaple.kimhabspringapikey.security.ApiKeyAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter  {

    private Logger log = LoggerFactory.getLogger(OncePerRequestFilter.class);
    private static final String API_KEY_HEADER = "X_API_KEY";
    String apiKeyConfig = "thisisanexmapleofapikeyheader";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String apiKey = request.getHeader(API_KEY_HEADER);

        StringBuilder headerInfo = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            headerInfo.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        });
        log.info("Header: {}", headerInfo);

        if (apiKeyConfig.equals(apiKey)) {
            log.info("API KEY matched");
            Authentication authentication = new ApiKeyAuth(apiKey);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.warn("No API key");
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
