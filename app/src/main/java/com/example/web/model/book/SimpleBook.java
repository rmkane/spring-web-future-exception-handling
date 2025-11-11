package com.example.web.model.book;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "SimpleBook", description = "Flattened book view for lightweight responses")
public class SimpleBook {
  @Schema(description = "Title of the work")
  private String title;

  @Schema(description = "Author name")
  private String authorName;

  @Schema(description = "Primary language")
  private String language;

  @Schema(description = "Publisher name")
  private String publisherName;
}
