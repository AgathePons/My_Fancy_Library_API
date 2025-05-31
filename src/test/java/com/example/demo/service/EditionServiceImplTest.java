package com.example.demo.service;

import com.example.demo.dto.EditionDto;
import com.example.demo.entities.Edition;
import com.example.demo.repository.EditionRepository;
import com.example.demo.service.impl.EditionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EditionServiceImplTest {

  private final Long idOne = 1L;
  private final Long idTwo = 2L;
  private final Long idNotFound = 999L;
  private final String nameOne = "edition 1";
  private final String nameTwo = "edition 2";
  private final Edition editionOne = Edition.builder().id(idOne).name(nameOne).build();
  private final Edition editionTwo = Edition.builder().id(idTwo).name(nameTwo).build();
  private final EditionDto editionDtoOne = EditionDto.builder().id(idOne).name(nameOne).build();
  private final EditionDto editionDtoTwo = EditionDto.builder().id(idTwo).name(nameTwo).build();
  private final List<Edition> editionList = Arrays.asList(this.editionOne, this.editionTwo);
  private final List<EditionDto> editionDtoList = Arrays.asList(this.editionDtoOne, this.editionDtoTwo);

  @Mock
  private EditionRepository editionRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private EditionServiceImpl editionService;

  @Test
  @DisplayName("findById when ID exists then should return Edition DTO")
  void findById_whenIdExists_shouldReturnEditionDto() {
    // Given
    when(editionRepository.findById(idOne)).thenReturn(Optional.of(editionOne));
    when(modelMapper.map(editionOne, EditionDto.class)).thenReturn(editionDtoOne);

    // When
    Optional<EditionDto> result = editionService.findById(idOne);

    // Then
    assertAll(
      "verify findById flow and result",
            () -> assertTrue(result.isPresent(), "Optional result is present"),
            () -> assertSame(editionDtoOne, result.get(), "expected Object and result Object are equal"),
            () -> verify(editionRepository).findById(idOne),
            () -> verify(modelMapper).map(editionOne, EditionDto.class)
    );
  }

  @Test
  @DisplayName("findById when ID does not exist then should return empty Optional")
  void findById_whenIdDoesNotExists_shouldReturnEmptyOptional() {
    // Given
    when(editionRepository.findById(idNotFound)).thenReturn(Optional.empty());

    // When
    Optional<EditionDto> result = editionService.findById(idNotFound);

    // Then
    assertAll(
            "verify findById flow and result",
            () -> assertFalse(result.isPresent(), "Optional result is not present"),
            () -> verify(editionRepository).findById(idNotFound),
            () -> verifyNoInteractions(modelMapper)
    );
  }
}
