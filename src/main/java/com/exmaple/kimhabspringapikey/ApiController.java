package com.exmaple.kimhabspringapikey;

import com.exmaple.kimhabspringapikey.config.ApiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    @Autowired
    private ApiConfig apiConfig;

    @GetMapping("/nonsecure")
    public String nonSecureEndpoint() {
        return apiConfig.getApiKey();
       // return "This is a non-secure endpoint.";
    }

    @GetMapping("/secure")
    public String secureEndpoint() {
        return "This is a secure endpoint.";
    }
}
