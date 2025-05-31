package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryDtoTest {

  @Test
  @DisplayName("test default constructor")
  void testDefaultConstructor() {
    var CategoryDto = new CategoryDto();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(CategoryDto.getId(), "id"),
            () -> assertNull(CategoryDto.getName(), "name")
    );
  }

  @Test
  @DisplayName("test all arguments constructor")
  void testAllArgsConstructor() {
    long id = 666;
    String name = "category name";
    var categoryDto = new CategoryDto(id, name);
    assertAll(
            "verify all dto attributes",
            () -> assertEquals(id, categoryDto.getId(), "id"),
            () -> assertEquals(name, categoryDto.getName(), "name")
    );
  }

  @Test
  @DisplayName("test builder")
  void testBuilder() {
    String name = "category name";
    var categoryDto = CategoryDto.builder().name(name).build();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(categoryDto.getId(), "id"),
            () -> assertEquals(name, categoryDto.getName(), "name")
    );
  }
}
