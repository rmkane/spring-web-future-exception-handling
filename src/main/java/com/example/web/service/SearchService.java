package com.example.web.service;

import com.example.web.search.SearchQuery;
import com.example.web.search.SearchResult;
import java.util.concurrent.CompletableFuture;

public interface SearchService {
  CompletableFuture<SearchResult> search(SearchQuery query);
}
