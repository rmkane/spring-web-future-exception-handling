package com.example.integration;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/** Utility to execute {@link RestRequest} using {@link RestTemplate}. */
public final class RestFetcher {
  private final RestTemplate restTemplate;

  public RestFetcher() {
    this(new RestTemplate());
  }

  public RestFetcher(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public <T> ResponseEntity<T> exchange(RestRequest request, Class<T> responseType) {
    HttpEntity<byte[]> entity = new HttpEntity<>(request.getBody(), request.getHeaders());
    return restTemplate.exchange(request.getURI(), request.getMethod(), entity, responseType);
  }

  public <T> ResponseEntity<T> exchange(
      RestRequest request, ParameterizedTypeReference<T> responseType) {
    HttpEntity<byte[]> entity = new HttpEntity<>(request.getBody(), request.getHeaders());
    return restTemplate.exchange(request.getURI(), request.getMethod(), entity, responseType);
  }
}
