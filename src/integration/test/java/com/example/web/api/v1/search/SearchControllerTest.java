package com.example.web.api.v1.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.integration.BaseControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Tag("integration")
final class SearchControllerTest extends BaseControllerTest {
  @Test
  void search_returnsResults_forFoo() throws IOException {
    var body = objectMapper.writeValueAsString(Map.of("term", "foo"));
    var request =
        request("/api/v1/search")
            .method(HttpMethod.POST)
            .headers(getDefaultHeaders())
            .body(body)
            .build();
    var response = restFetcher.fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "search_returnsResults_forFoo.json");

    var json =
        objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
    assertEquals("foo", ((Map<?, ?>) json.get("query")).get("term"));
  }

  @Test
  void search_returnsNoContent_forError() throws JsonProcessingException {
    var body = objectMapper.writeValueAsString(Map.of("term", "error"));
    var request =
        request("/api/v1/search")
            .method(HttpMethod.POST)
            .headers(getDefaultHeaders())
            .body(body)
            .build();
    var response = restFetcher.fetch(request, String.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }
}
