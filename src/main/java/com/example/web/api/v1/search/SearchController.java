package com.example.web.api.v1.search;

import com.example.web.model.search.SearchQuery;
import com.example.web.model.search.SearchResult;
import com.example.web.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/search", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Search", description = "Search endpoints")
@RequiredArgsConstructor
@Slf4j
public class SearchController {
  private final SearchService searchService;

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @Operation(
      summary = "Search",
      description = "Executes a search for a given query term",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = SearchQuery.class))),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = SearchResult.class))),
        @ApiResponse(responseCode = "204", description = "No Content")
      })
  public CompletableFuture<ResponseEntity<SearchResult>> search(@RequestBody SearchQuery query) {
    log.error("Searching for: {}", query);
    return searchService.search(query).thenApply(ResponseEntity::ok);
  }
}
