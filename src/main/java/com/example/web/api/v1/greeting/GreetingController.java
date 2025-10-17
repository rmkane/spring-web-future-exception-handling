package com.example.web.api.v1.greeting;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
public class GreetingController {

  @GetMapping(path = {"", "/"})
  public ResponseEntity<Map<String, Object>> root() {
    return ResponseEntity.ok(
        Map.ofEntries(Map.entry("message", "Hello from API v1"), Map.entry("version", "v1")));
  }
}
