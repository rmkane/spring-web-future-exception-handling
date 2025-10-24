package com.example.web.model.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
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
    name = "Copyright",
    propOrder = {"holder", "year", "notice"})
@Schema(name = "Copyright", description = "Copyright holder, year and notice")
public class Copyright {
  @XmlElement(name = "Holder", namespace = "http://example.com/book", required = true)
  private String holder;

  @XmlElement(name = "Year", namespace = "http://example.com/book", required = true)
  private Integer year;

  @XmlElement(name = "Notice", namespace = "http://example.com/book")
  private String notice;
}
