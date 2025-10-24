package com.example.web.api.v1.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.integration.RestFetcher;
import com.example.integration.RestRequest;
import com.example.integration.RestRequestBuilder;
import com.example.web.api.BaseControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Tag("integration")
final class SearchControllerTest extends BaseControllerTest {
  @Test
  void search_returnsResults_forFoo() throws IOException {
    String body = objectMapper.writeValueAsString(Map.of("term", "foo"));
    RestFetcher fetcher = new RestFetcher();
    RestRequest request =
        RestRequestBuilder.create(BASE_URL, "/api/v1/search")
            .method(HttpMethod.POST)
            .headers(getDefaultHeaders())
            .body(body)
            .build();
    ResponseEntity<String> response = fetcher.exchange(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "search_returnsResults_forFoo.json");

    Map<String, Object> json =
        objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
    assertEquals("foo", ((Map<?, ?>) json.get("query")).get("term"));
  }

  @Test
  void search_returnsNoContent_forError() throws JsonProcessingException {
    String body = objectMapper.writeValueAsString(Map.of("term", "error"));
    RestFetcher fetcher = new RestFetcher();
    RestRequest request =
        RestRequestBuilder.create("http://localhost:8080", "/api/v1/search")
            .method(HttpMethod.POST)
            .headers(getDefaultHeaders())
            .body(body)
            .build();
    ResponseEntity<String> response = fetcher.exchange(request, String.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }
}
