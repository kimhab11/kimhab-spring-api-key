package com.exmaple.kimhabspringapikey.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class ApiKeyAuth implements Authentication {
    private final String apiKey;
    private boolean authenticated = true;

    public ApiKeyAuth(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
