package com.exmaple.kimhabspringapikey.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiConfig {

    @Value("${header.apikey}")
    private String apiKey;

    public String getApiKey(){
        return this.apiKey;
    }
}
