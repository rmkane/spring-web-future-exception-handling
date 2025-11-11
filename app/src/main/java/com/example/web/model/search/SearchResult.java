package com.example.web.model.search;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchResult {
  private SearchQuery query;
  private List<Map<String, Object>> results;
}
