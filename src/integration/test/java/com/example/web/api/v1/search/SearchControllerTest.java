package com.example.web.api.v1.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.web.api.BaseControllerTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SearchControllerTest extends BaseControllerTest {
  @Test
  void search_returnsResults_forFoo() throws IOException {
    RestTemplate restTemplate = new RestTemplate();
    String body = objectMapper.writeValueAsString(Map.of("term", "foo"));
    HttpEntity<String> request = new HttpEntity<>(body, getDefaultHeaders());

    ResponseEntity<String> response =
        restTemplate.exchange(
            "http://localhost:8080/api/v1/search", HttpMethod.POST, request, String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    writeJsonResponse(response.getBody(), "search_returnsResults_forFoo.json");

    Map<String, Object> json =
        objectMapper.readValue(response.getBody(), new TypeReference<Map<String, Object>>() {});
    assertEquals("foo", ((Map<?, ?>) json.get("query")).get("term"));
  }

  @Test
  void search_returnsNoContent_forError() throws JsonProcessingException {
    RestTemplate restTemplate = new RestTemplate();
    String body = objectMapper.writeValueAsString(Map.of("term", "error"));
    HttpEntity<String> request = new HttpEntity<>(body, getDefaultHeaders());

    ResponseEntity<String> response =
        restTemplate.exchange(
            "http://localhost:8080/api/v1/search", HttpMethod.POST, request, String.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }
}
