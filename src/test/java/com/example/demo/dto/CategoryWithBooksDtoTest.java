package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryWithBooksDtoTest {
  @Test
  @DisplayName("test default constructor")
  void testDefaultConstructor() {
    var categoryWithBooksDto = new CategoryWithBooksDto();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(categoryWithBooksDto.getId(), "id"),
            () -> assertNull(categoryWithBooksDto.getName(), "name"),
            () -> assertEquals(0, categoryWithBooksDto.getBooks().size(), "books list size")
    );
  }

  @Test
  @DisplayName("test all arguments constructor")
  void testAllArgsConstructor() {
    long id = 666;
    String name = "category name";
    var bookDtoOne = new BookDto();
    var bookDtoTwo = new BookDto();
    List<BookDto> bookDtoList = List.of(bookDtoOne, bookDtoTwo);
    var categoryWithBooksDto = new CategoryWithBooksDto(id, name, bookDtoList);
    assertAll(
            "verify all dto attributes",
            () -> assertEquals(id, categoryWithBooksDto.getId(), "id"),
            () -> assertEquals(name, categoryWithBooksDto.getName(), "name"),
            () -> assertEquals(bookDtoList, categoryWithBooksDto.getBooks(), "books"),
            () -> assertEquals(2, categoryWithBooksDto.getBooks().size(), "books list size")
    );
  }

  @Test
  @DisplayName("test builder")
  void testBuilder() {
    String name = "category name";
    var bookDtoOne = new BookDto();
    var bookDtoTwo = new BookDto();
    List<BookDto> bookDtoList = List.of(bookDtoOne, bookDtoTwo);
    var categoryWithBooksDto = CategoryWithBooksDto.builder().name(name).books(bookDtoList).build();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(categoryWithBooksDto.getId(), "id"),
            () -> assertEquals(name, categoryWithBooksDto.getName(), "name"),
            () -> assertEquals(bookDtoList, categoryWithBooksDto.getBooks(), "books"),
            () -> assertEquals(2, categoryWithBooksDto.getBooks().size(), "books list size")
    );
  }
}
