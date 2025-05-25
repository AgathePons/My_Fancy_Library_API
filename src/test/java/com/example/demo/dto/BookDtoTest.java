package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookDtoTest {
  @Test
  @DisplayName("test default constructor")
  void testDefaultConstructor() {
    var bookDto = new BookDto();
    assertAll(
            () -> assertNull(bookDto.getId(), "id"),
            () -> assertNull(bookDto.getTitle(), "title"),
            () -> assertNull(bookDto.getAbstractText(), "abstract text"),
            () -> assertNull(bookDto.getYearOfRelease(), "year of release"),
            () -> assertNull(bookDto.getCover(), "cover"),
            () -> assertNull(bookDto.getAuthor(), "author"),
            () -> assertNull(bookDto.getEdition(), "edition"),
            () -> assertEquals(0, bookDto.getCategories().size(), "categories list size")

    );
  }

  @Test
  @DisplayName("test all arguments constructor")
  void testAllArgsConstructor() {
    long id = 666;
    String title = "book title";
    String abstractText = "book abstract text";
    int yearOfRelease = 2012;
    String cover = "cover.png";
    String author = "firstname lastname";
    String edition = "book edition";
    String categoryOne = "book category one";
    String categoryTwo = "book category two";
    List<String> categoryList = List.of(categoryOne, categoryTwo);
    var bookDto = new BookDto(id,title, abstractText, yearOfRelease, cover, author, edition, categoryList);
    assertAll(
            () -> assertEquals(id, bookDto.getId(), "id"),
            () -> assertEquals(title, bookDto.getTitle(), "title"),
            () -> assertEquals(abstractText, bookDto.getAbstractText(), "abstract text"),
            () -> assertEquals(yearOfRelease, bookDto.getYearOfRelease(), "year of release"),
            () -> assertEquals(cover, bookDto.getCover(), "cover"),
            () -> assertEquals(author, bookDto.getAuthor(), "author"),
            () -> assertEquals(edition, bookDto.getEdition(), "edition"),
            () -> assertEquals(categoryList, bookDto.getCategories(), "categories"),
            () -> assertEquals(2, bookDto.getCategories().size(), "categories list size")
    );
  }

  @Test
  @DisplayName("test builder")
  void testBuilder() {
    String title = "book title";
    String abstractText = "book abstract text";
    int yearOfRelease = 2012;
    String cover = "cover.png";
    String author = "firstname lastname";
    String edition = "book edition";
    String categoryOne = "book category one";
    String categoryTwo = "book category two";
    List<String> categoryList = List.of(categoryOne, categoryTwo);
    var bookDto = BookDto.builder()
            .title(title)
            .abstractText(abstractText)
            .yearOfRelease(yearOfRelease)
            .cover(cover)
            .author(author)
            .edition(edition)
            .categories(categoryList)
            .build();
    assertAll(
            () -> assertNull(bookDto.getId(), "id"),
            () -> assertEquals(title, bookDto.getTitle(), "title"),
            () -> assertEquals(abstractText, bookDto.getAbstractText(), "abstract text"),
            () -> assertEquals(yearOfRelease, bookDto.getYearOfRelease(), "year of release"),
            () -> assertEquals(cover, bookDto.getCover(), "cover"),
            () -> assertEquals(author, bookDto.getAuthor(), "author"),
            () -> assertEquals(edition, bookDto.getEdition(), "edition"),
            () -> assertEquals(categoryList, bookDto.getCategories(), "categories"),
            () -> assertEquals(2, bookDto.getCategories().size(), "categories list size")
    );
  }
}
