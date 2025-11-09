package com.example.integration;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
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
    try {
      HttpEntity<?> entity;
      if (request.isMultipart()) {
        entity = new HttpEntity<>(request.getMultipartBody(), request.getHeaders());
      } else {
        entity = new HttpEntity<>(request.getBody(), request.getHeaders());
      }
      return restTemplate.exchange(request.getURI(), request.getMethod(), entity, responseType);
    } catch (HttpClientErrorException e) {
      return ResponseEntity.status(e.getStatusCode().value()).body((T) e.getResponseBodyAsString());
    }
  }

  public <T> ResponseEntity<T> exchange(
      RestRequest request, ParameterizedTypeReference<T> responseType) {
    try {
      HttpEntity<?> entity;
      if (request.isMultipart()) {
        entity = new HttpEntity<>(request.getMultipartBody(), request.getHeaders());
      } else {
        entity = new HttpEntity<>(request.getBody(), request.getHeaders());
      }
      return restTemplate.exchange(request.getURI(), request.getMethod(), entity, responseType);
    } catch (HttpClientErrorException e) {
      return ResponseEntity.status(e.getStatusCode().value()).body((T) e.getResponseBodyAsString());
    }
  }
}
