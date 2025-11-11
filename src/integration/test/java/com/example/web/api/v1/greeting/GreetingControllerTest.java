package com.example.web.api.v1.greeting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.integration.BaseControllerTest;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Tag("integration")
final class GreetingControllerTest extends BaseControllerTest {
  @Test
  void testRootReturnsHelloFromApiV1() throws IOException {
    var request = this.request("/api/v1/greeting").headers(getDefaultHeaders()).build();
    var response = restFetcher.fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "greeting_returnsHelloFromApiV1.json");

    var body =
        objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});

    assertEquals("Hello from API v1", body.get("message"));
    assertEquals("v1", body.get("version"));
  }
}
