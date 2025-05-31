package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BookFullDtoTest {
  @Test
  @DisplayName("test default constructor")
  void testDefaultConstructor() {
    var bookFullDto = new BookFullDto();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(bookFullDto.getId(), "id"),
            () -> assertNull(bookFullDto.getTitle(), "title"),
            () -> assertNull(bookFullDto.getAbstractText(), "abstract text"),
            () -> assertNull(bookFullDto.getYearOfRelease(), "year of release"),
            () -> assertNull(bookFullDto.getCover(), "cover"),
            () -> assertNull(bookFullDto.getAuthor(), "author"),
            () -> assertNull(bookFullDto.getEdition(), "edition"),
            () -> assertEquals(0, bookFullDto.getCategories().size(), "categories list size")
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
    var authorDto = new AuthorDto();
    var editionDto = new EditionDto();
    var categoryDtoOne = new CategoryDto();
    var categoryDtoTwo = new CategoryDto();
    List<CategoryDto> categoryDtoList = List.of(categoryDtoOne, categoryDtoTwo);
    var bookFullDto = new BookFullDto(id, title, abstractText, yearOfRelease, cover, authorDto, editionDto, categoryDtoList);
    assertAll(
            "verify all dto attributes",
            () -> assertEquals(id, bookFullDto.getId(), "id"),
            () -> assertEquals(title, bookFullDto.getTitle(), "title"),
            () -> assertEquals(abstractText, bookFullDto.getAbstractText(), "abstract text"),
            () -> assertEquals(yearOfRelease, bookFullDto.getYearOfRelease(), "year of release"),
            () -> assertEquals(cover, bookFullDto.getCover(), "cover"),
            () -> assertEquals(authorDto, bookFullDto.getAuthor(), "author"),
            () -> assertEquals(editionDto, bookFullDto.getEdition(), "edition"),
            () -> assertEquals(categoryDtoList, bookFullDto.getCategories(), "categories"),
            () -> assertEquals(2, bookFullDto.getCategories().size(), "categories list size")
    );
  }

  @Test
  @DisplayName("test builder")
  void testBuilder() {
    String title = "book title";
    String abstractText = "book abstract text";
    int yearOfRelease = 2012;
    String cover = "cover.png";
    var authorDto = new AuthorDto();
    var editionDto = new EditionDto();
    var categoryDtoOne = new CategoryDto();
    var categoryDtoTwo = new CategoryDto();
    List<CategoryDto> categoryDtoList = List.of(categoryDtoOne, categoryDtoTwo);
    var bookFullDto = BookFullDto.builder()
            .title(title)
            .abstractText(abstractText)
            .yearOfRelease(yearOfRelease)
            .cover(cover)
            .author(authorDto)
            .edition(editionDto)
            .categories(categoryDtoList)
            .build();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(bookFullDto.getId(), "id"),
            () -> assertEquals(title, bookFullDto.getTitle(), "title"),
            () -> assertEquals(abstractText, bookFullDto.getAbstractText(), "abstract text"),
            () -> assertEquals(yearOfRelease, bookFullDto.getYearOfRelease(), "year of release"),
            () -> assertEquals(cover, bookFullDto.getCover(), "cover"),
            () -> assertEquals(authorDto, bookFullDto.getAuthor(), "author"),
            () -> assertEquals(editionDto, bookFullDto.getEdition(), "edition"),
            () -> assertEquals(categoryDtoList, bookFullDto.getCategories(), "categories"),
            () -> assertEquals(2, bookFullDto.getCategories().size(), "categories list size")
    );
  }
}
