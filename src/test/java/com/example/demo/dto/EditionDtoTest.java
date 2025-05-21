package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EditionDtoTest {

  @Test
  @DisplayName("test default constructor")
  void testDefaultConstructor() {
    var editionDto = new EditionDto();
    assertAll(
            () -> assertNull(editionDto.getId(), "id"),
            () -> assertNull(editionDto.getName(), "name")
    );
  }

  @Test
  @DisplayName("test all arguments constructor")
  void testAllArgsConstructor() {
    long id = 666;
    String name = "edition name";
    var editionDto = new EditionDto(id, name);
    assertAll(
            () -> assertEquals(id, editionDto.getId(), "id"),
            () -> assertEquals(name, editionDto.getName(), "name")
    );
  }

  @Test
  @DisplayName("test builder")
  void testBuilder() {
    String name = "edition name";
    var editionDto = EditionDto.builder().name(name).build();
    assertAll(
            () -> assertNull(editionDto.getId(), "id"),
            () -> assertEquals(name, editionDto.getName(), "name")
    );
  }
}
