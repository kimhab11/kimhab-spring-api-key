package com.exmaple.kimhabspringapikey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Ensures web server starts
class KimhabSpringApiKeyApplicationTests {

    private int port = 8080;
    @Autowired
    private TestRestTemplate restTemplate;

    private static final String API_KEY_HEADER = "x-api-key";
    private static final String VALID_API_KEY = "thisisanexmapleofapikeyheader";



    @Test
    void testSecuredApiWithValidApiKey() {
        String url = "http://localhost:" + port + "/api/v1/secure";

        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY_HEADER.toUpperCase(), VALID_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Secure Data!", response.getBody());
    }
    @Test
    void testSecuredApiWithInvalidApiKey() {
        String url = "http://localhost:" + port + "/api/v1/secure";

        HttpHeaders headers = new HttpHeaders();
        headers.set(API_KEY_HEADER, "invalid-api-key");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        assertEquals(401, response.getStatusCodeValue()); // Expect Unauthorized status
    }

}
