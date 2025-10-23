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
    name = "PhysicalType",
    propOrder = {"format", "pages"})
@Schema(name = "Physical", description = "Physical characteristics of the book")
public class PhysicalType {
  @XmlElement(name = "Format", namespace = "http://example.com/book", required = true)
  private String format;

  @XmlElement(name = "Pages", namespace = "http://example.com/book", required = true)
  private Integer pages;
}
