package com.example.web.api.v1.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.Map;
import org.example.integration.BaseControllerTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@Tag("integration")
final class SearchControllerTest extends BaseControllerTest {
  @Test
  void search_returnsResults_forFoo() throws IOException {
    var body = toJson(Map.of("term", "foo"));
    var request = post("/api/v1/search").headers(getDefaultHeaders()).body(body).build();
    var response = restFetcher.fetch(request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "search_returnsResults_forFoo.json");

    var json = parseJsonResponse(response);
    assertEquals("foo", ((Map<?, ?>) json.get("query")).get("term"));
  }

  @Test
  void search_returnsNoContent_forError() throws JsonProcessingException {
    var body = toJson(Map.of("term", "error"));
    var request = post("/api/v1/search").headers(getDefaultHeaders()).body(body).build();
    var response = restFetcher.fetch(request, String.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }
}
