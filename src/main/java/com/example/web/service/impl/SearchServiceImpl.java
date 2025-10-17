package com.example.web.service.impl;

import com.example.web.exception.SearchException;
import com.example.web.search.SearchQuery;
import com.example.web.search.SearchResult;
import com.example.web.service.SearchService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
  @Override
  public CompletableFuture<SearchResult> search(SearchQuery query) {
    return CompletableFuture.supplyAsync(
        () -> {
          log.info("Searching for: {}", query);

          if (query.getTerm().equals("error")) {
            throw new SearchException("Error searching for: " + query.getTerm());
          }

          return SearchResult.builder().query(query).results(List.of()).build();
        });
  }
}
