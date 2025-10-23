package com.example.web.api.v1.greeting;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Greeting", description = "Greeting endpoints")
public class GreetingController {

  @GetMapping
  @Operation(
      summary = "Greeting root",
      description = "Returns a simple greeting with API version.",
      responses =
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content =
                  @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = Map.class))))
  public ResponseEntity<Map<String, Object>> root() {
    return ResponseEntity.ok(
        Map.ofEntries(Map.entry("message", "Hello from API v1"), Map.entry("version", "v1")));
  }
}
