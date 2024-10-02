package com.exmaple.kimhabspringapikey.config;

import com.exmaple.kimhabspringapikey.security.ApiKeyAuthFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.addHeader("access_denied_reason", "not_authorized");
            log.error("AccessDeniedHandler: {}",accessDeniedException.toString());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");

        };
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authenticationEntryPointException) -> {
            log.error("AuthenticationEntryPoint: {}", authenticationEntryPointException.getLocalizedMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "unauthorized");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(new ApiKeyAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/nonsecure").permitAll()
                .antMatchers("/api/v1/secure").authenticated()
                .anyRequest().authenticated();
        return http.build();
    }
}
