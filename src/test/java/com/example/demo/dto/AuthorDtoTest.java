package com.example.demo.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorDtoTest {
  @Test
  @DisplayName("test default constructor")
  void testDefaultConstructor() {
    var authorDto = new AuthorDto();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(authorDto.getId(), "id"),
            () -> assertNull(authorDto.getFirstname(), "firstname"),
            () -> assertNull(authorDto.getLastname(), "lastname")
    );
  }

  @Test
  @DisplayName("test all arguments constructor")
  void testAllArgsConstructor() {
    long id = 666;
    String firstname = "author firstname";
    String lastname = "author lastname";
    var authorDto = new AuthorDto(id, firstname, lastname);
    assertAll(
            "verify all dto attributes",
            () -> assertEquals(id, authorDto.getId()),
            () -> assertEquals(firstname, authorDto.getFirstname()),
            () -> assertEquals(lastname, authorDto.getLastname())
    );
  }

  @Test
  @DisplayName("test builder")
  void testBuilder() {
    String firstname = "author firstname";
    String lastname = "author lastname";
    var authorDto = AuthorDto.builder().firstname(firstname).lastname(lastname).build();
    assertAll(
            "verify all dto attributes",
            () -> assertNull(authorDto.getId(), "id"),
            () -> assertEquals(firstname, authorDto.getFirstname()),
            () -> assertEquals(lastname, authorDto.getLastname())
    );
  }
}
