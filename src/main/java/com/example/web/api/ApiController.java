package com.example.web.api;

import io.swagger.v3.oas.annotations.Hidden;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

  @GetMapping(path = {"", "/"})
  public ResponseEntity<Map<String, Object>> root() {
    return ResponseEntity.ok(
        Map.ofEntries(
            Map.entry("versions", new String[] {"v1"}),
            Map.entry("links", Map.ofEntries(Map.entry("v1", "/api/v1")))));
  }
}
