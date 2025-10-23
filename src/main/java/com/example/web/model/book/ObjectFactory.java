package com.example.web.model.book;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
  public ObjectFactory() {}

  public Book createBook() {
    return new Book();
  }

  public AuthorType createAuthorType() {
    return new AuthorType();
  }

  public TranslatorType createTranslatorType() {
    return new TranslatorType();
  }

  public PublisherType createPublisherType() {
    return new PublisherType();
  }

  public IdentifiersType createIdentifiersType() {
    return new IdentifiersType();
  }

  public SubjectsType createSubjectsType() {
    return new SubjectsType();
  }

  public PhysicalType createPhysicalType() {
    return new PhysicalType();
  }

  public ChapterType createChapterType() {
    return new ChapterType();
  }

  public ChaptersType createChaptersType() {
    return new ChaptersType();
  }

  public QuotesType createQuotesType() {
    return new QuotesType();
  }

  public RightsType createRightsType() {
    return new RightsType();
  }

  public CopyrightType createCopyrightType() {
    return new CopyrightType();
  }
}
