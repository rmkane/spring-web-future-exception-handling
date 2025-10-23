package com.example.web.model.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.CollapsedStringAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Book", namespace = "http://example.com/book")
@XmlType(
    name = "BookType",
    propOrder = {
      "title",
      "subtitle",
      "author",
      "translator",
      "publisher",
      "identifiers",
      "language",
      "originalLanguage",
      "description",
      "subjects",
      "physical",
      "chapters",
      "quotes",
      "rights"
    })
@Schema(name = "Book", description = "Book metadata, content, and rights information")
public class Book {
  @XmlElement(name = "Title", namespace = "http://example.com/book", required = true)
  @Schema(description = "Title of the work", example = "Don Quixote")
  private String title;

  @XmlElement(name = "Subtitle", namespace = "http://example.com/book")
  @Schema(description = "Optional subtitle", example = "The Ingenious Gentleman of La Mancha")
  private String subtitle;

  @XmlElement(name = "Author", namespace = "http://example.com/book", required = true)
  @Schema(description = "Primary author metadata")
  private AuthorType author;

  @XmlElement(name = "Translator", namespace = "http://example.com/book")
  @Schema(description = "Translator information if applicable")
  private TranslatorType translator;

  @XmlElement(name = "Publisher", namespace = "http://example.com/book", required = true)
  @Schema(description = "Publisher information")
  private PublisherType publisher;

  @XmlElement(name = "Identifiers", namespace = "http://example.com/book")
  @Schema(description = "External identifiers e.g., ISBN")
  private IdentifiersType identifiers;

  @XmlElement(name = "Language", namespace = "http://example.com/book", required = true)
  @Schema(description = "Language of the book text", example = "English")
  private String language;

  @XmlElement(name = "OriginalLanguage", namespace = "http://example.com/book")
  @Schema(description = "Original language if this is a translation", example = "Spanish")
  private String originalLanguage;

  @XmlElement(name = "Description", namespace = "http://example.com/book")
  @Schema(description = "Synopsis or description of the book")
  @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
  private String description;

  @XmlElement(name = "Subjects", namespace = "http://example.com/book")
  @Schema(description = "Subject tags/categories")
  private SubjectsType subjects;

  @XmlElement(name = "Physical", namespace = "http://example.com/book")
  @Schema(description = "Physical characteristics like format and page count")
  private PhysicalType physical;

  @XmlElement(name = "Chapters", namespace = "http://example.com/book", required = true)
  @Schema(description = "Chapters list")
  private ChaptersType chapters;

  @XmlElement(name = "Quotes", namespace = "http://example.com/book")
  @Schema(description = "Selected notable quotes")
  private QuotesType quotes;

  @XmlElement(name = "Rights", namespace = "http://example.com/book")
  @Schema(description = "Copyright and rights information")
  private RightsType rights;

  @XmlAttribute(name = "lang", namespace = "http://www.w3.org/XML/1998/namespace")
  @Schema(description = "RFC 5646 language tag for the document", example = "en")
  private String lang;

  @XmlAttribute(name = "id")
  @Schema(description = "Unique ID for the document", example = "bk-don-quixote")
  private String id;

  @XmlAttribute(name = "version")
  @Schema(description = "Schema or document version", example = "1.0")
  private String version;
}
