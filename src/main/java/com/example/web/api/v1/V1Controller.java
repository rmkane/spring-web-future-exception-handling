package com.example.web.api.v1;

import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class V1Controller {

  @GetMapping(path = {"", "/"})
  public ResponseEntity<Map<String, Object>> discovery() {
    return ResponseEntity.ok(
        Map.ofEntries(
            Map.entry("version", "v1"),
            Map.entry(
                "endpoints",
                List.of(
                    Map.entry("method", "GET"),
                    Map.entry("path", "/api/v1"),
                    Map.entry("description", "API v1 discovery")))));
  }
}
