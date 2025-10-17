package com.example.web.advice;

import com.example.web.exception.SearchException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice(basePackages = "com.example.web.api")
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<Map<String, Object>> handleResponseStatus(
      ResponseStatusException ex, HttpServletRequest request) {
    log.error("Response status exception: {}", ex.getMessage());
    HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
    return ResponseEntity.status(status).body(errorBody(status, ex.getReason(), request));
  }

  @ExceptionHandler({
    IllegalArgumentException.class,
    IllegalStateException.class,
    MissingServletRequestParameterException.class,
    HttpMessageNotReadableException.class
  })
  public ResponseEntity<Map<String, Object>> handleBadRequest(
      Exception ex, HttpServletRequest request) {
    log.error("Bad request exception: {}", ex.getMessage());
    HttpStatus status = HttpStatus.BAD_REQUEST; // 400
    return ResponseEntity.status(status).body(errorBody(status, ex.getMessage(), request));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleUnknown(
      Exception ex, HttpServletRequest request) {
    log.error("Unknown exception: {}", ex.getMessage());
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
    return ResponseEntity.status(status).body(errorBody(status, ex.getMessage(), request));
  }

  @ExceptionHandler(SearchException.class)
  public ResponseEntity<Map<String, Object>> handleDefaultException(
      SearchException ex, HttpServletRequest request) {
    log.error("Search exception: {}", ex.getMessage());
    HttpStatus status = HttpStatus.NO_CONTENT; // 204
    return ResponseEntity.status(status).body(errorBody(status, ex.getMessage(), request));
  }

  private Map<String, Object> errorBody(
      HttpStatus status, String message, HttpServletRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", OffsetDateTime.now().toString());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    body.put("path", request.getRequestURI());
    return body;
  }
}
