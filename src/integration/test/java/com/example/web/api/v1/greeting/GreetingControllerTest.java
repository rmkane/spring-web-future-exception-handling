package com.example.web.api.v1.greeting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.integration.RestFetcher;
import com.example.integration.RestRequest;
import com.example.integration.RestRequestBuilder;
import com.example.web.api.BaseControllerTest;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("integration")
final class GreetingControllerTest extends BaseControllerTest {
  @Test
  void testRootReturnsHelloFromApiV1() throws IOException {
    RestFetcher fetcher = new RestFetcher();
    RestRequest request =
        RestRequestBuilder.create("http://localhost:8080", "/api/v1/greeting")
            .headers(getDefaultHeaders())
            .build();
    ResponseEntity<String> response = fetcher.exchange(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "greeting_returnsHelloFromApiV1.json");

    Map<String, Object> body =
        objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

    assertEquals("Hello from API v1", body.get("message"));
    assertEquals("v1", body.get("version"));
  }
}
