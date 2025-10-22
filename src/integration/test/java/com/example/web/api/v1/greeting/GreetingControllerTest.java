package com.example.web.api.v1.greeting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.web.api.BaseControllerTest;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Tag("integration")
public class GreetingControllerTest extends BaseControllerTest {
  @Test
  void testRootReturnsHelloFromApiV1() throws IOException {
    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<Void> request = new HttpEntity<>(getDefaultHeaders());
    ResponseEntity<String> response =
        restTemplate.exchange(
            "http://localhost:8080/api/v1/greeting", HttpMethod.GET, request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "greeting_returnsHelloFromApiV1.json");

    Map<String, Object> body =
        objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

    assertEquals("Hello from API v1", body.get("message"));
    assertEquals("v1", body.get("version"));
  }
}
