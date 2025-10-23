package com.example.web.model.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "ChapterType",
    propOrder = {"summary"})
@Schema(name = "Chapter", description = "Single chapter metadata")
public class ChapterType {
  @XmlElement(name = "Summary", namespace = "http://example.com/book")
  private String summary;

  @XmlAttribute(name = "number", required = true)
  private Integer number;

  @XmlAttribute(name = "title", required = true)
  private String title;
}
