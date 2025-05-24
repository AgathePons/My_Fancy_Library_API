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
            () -> assertNull(categoryWithBooksDto.getId(), "id"),
            () -> assertNull(categoryWithBooksDto.getName(), "name"),
            () -> assertNull(categoryWithBooksDto.getBooks(), "books")
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
            () -> assertEquals(id, categoryWithBooksDto.getId()),
            () -> assertEquals(name, categoryWithBooksDto.getName()),
            () -> assertEquals(bookDtoList, categoryWithBooksDto.getBooks()),
            () -> assertEquals(2, categoryWithBooksDto.getBooks().size())
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
            () -> assertNull(categoryWithBooksDto.getId(), "id"),
            () -> assertEquals(name, categoryWithBooksDto.getName()),
            () -> assertEquals(bookDtoList, categoryWithBooksDto.getBooks()),
            () -> assertEquals(2, categoryWithBooksDto.getBooks().size())
    );
  }
}
