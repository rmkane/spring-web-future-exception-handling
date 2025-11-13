package com.example.web.api.v1.greeting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.example.integration.IntegrationTestSuite;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Tag("integration")
final class GreetingControllerTest extends IntegrationTestSuite {
  @Test
  void testRootReturnsHelloFromApiV1() throws IOException {
    var request = request("/api/v1/greeting").headers(getDefaultHeaders()).build();
    var response = fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "greeting_returnsHelloFromApiV1.json");

    var body = parseJsonResponse(response);

    assertEquals("Hello from API v1", body.get("message"));
    assertEquals("v1", body.get("version"));
  }
}
