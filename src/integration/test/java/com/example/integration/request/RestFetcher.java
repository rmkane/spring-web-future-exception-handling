package com.example.integration.request;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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

  public <T> ResponseEntity<T> fetch(RestRequest request, @NonNull Class<T> responseType) {
    try {
      HttpEntity<?> entity =
          request.isMultipart()
              ? new HttpEntity<>(request.getMultipartBody(), request.getHeaders())
              : new HttpEntity<>(request.getBody(), request.getHeaders());

      return restTemplate.exchange(request.getURI(), request.getMethod(), entity, responseType);

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      // Return the raw error body as T when possible (common case: String).
      @SuppressWarnings("unchecked")
      T body = (T) e.getResponseBodyAsString();
      return ResponseEntity.status(e.getStatusCode())
          .headers(e.getResponseHeaders() != null ? e.getResponseHeaders() : null)
          .body(body);
    }
  }

  public <T> ResponseEntity<T> fetch(
      RestRequest request, @NonNull ParameterizedTypeReference<T> responseType) {
    try {
      HttpEntity<?> entity =
          request.isMultipart()
              ? new HttpEntity<>(request.getMultipartBody(), request.getHeaders())
              : new HttpEntity<>(request.getBody(), request.getHeaders());

      return restTemplate.exchange(request.getURI(), request.getMethod(), entity, responseType);

    } catch (HttpClientErrorException | HttpServerErrorException e) {
      @SuppressWarnings("unchecked")
      T body = (T) e.getResponseBodyAsString();
      return ResponseEntity.status(e.getStatusCode())
          .headers(e.getResponseHeaders() != null ? e.getResponseHeaders() : null)
          .body(body);
    }
  }
}
