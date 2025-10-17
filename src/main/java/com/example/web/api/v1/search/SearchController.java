package com.example.web.api.v1.search;

import com.example.web.search.SearchQuery;
import com.example.web.search.SearchResult;
import com.example.web.service.SearchService;
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
@RequiredArgsConstructor
@Slf4j
public class SearchController {
  private final SearchService searchService;

  @PostMapping(
      path = {"", "/"},
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public CompletableFuture<ResponseEntity<SearchResult>> search(@RequestBody SearchQuery query) {
    return searchService.search(query).thenApply(ResponseEntity::ok);
  }
}
