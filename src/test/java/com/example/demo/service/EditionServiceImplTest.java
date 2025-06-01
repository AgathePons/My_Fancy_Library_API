package com.example.demo.service;

import com.example.demo.dto.EditionDto;
import com.example.demo.entities.Edition;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.EditionRepository;
import com.example.demo.service.impl.EditionServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
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
  private final Long idToAdd = 10L;
  private final String nameOne = "edition 1";
  private final String nameTwo = "edition 2";
  private final String nameToAdd = "edition to add";
  private final Edition editionOne = Edition.builder().id(idOne).name(nameOne).build();
  private final Edition editionTwo = Edition.builder().id(idTwo).name(nameTwo).build();
  private final Edition editionToAdd = Edition.builder().name(nameToAdd).build();
  private final EditionDto editionDtoOne = EditionDto.builder().id(idOne).name(nameOne).build();
  private final EditionDto editionDtoTwo = EditionDto.builder().id(idTwo).name(nameTwo).build();
  private final EditionDto editionDtoToAdd = EditionDto.builder().name(nameToAdd).build();
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
            () -> assertSame(editionDtoOne, result.get(), "expected Object and result Object are the same"),
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

  @Test
  @DisplayName("findAll should return a list of EditionDto")
  void findAll_shouldReturnEditionDtoList() {
    // Given
    List<EditionDto> result;
    List<EditionDto> resultEditionDtoList = editionDtoList;
    when(editionRepository.findAll()).thenReturn(editionList);
    Iterable<Edition> resultEditionList = editionRepository.findAll();
    try(MockedStatic<ModelMapperUtil> mockedStatic = mockStatic(ModelMapperUtil.class)) {

      mockedStatic.when(() -> ModelMapperUtil.mapList(resultEditionList, EditionDto.class))
              .thenReturn(resultEditionDtoList);

      // When
      result = editionService.findAll();

      // Then
      assertAll(
              "verify findAll flow and result",
              () -> assertNotNull(result, "result is not null"),
              () -> assertEquals(2, result.size(), "list should have 2 elements"),
              () -> assertIterableEquals(editionDtoList, result, "expected list and result list are equal Iterable"),
              () -> assertEquals(editionDtoList, result, "expected list should match result"),
              () -> assertSame(editionDtoList, result, "expected list and result should be the same object"),
              () -> verify(editionRepository, times(2)).findAll(),
              () -> mockedStatic.verify(() -> ModelMapperUtil.mapList(resultEditionList, EditionDto.class))
      );
    }
  }

  @Test
  @DisplayName("add should return the new EditionDto with ID")
  void add_shouldReturnNewEditionDtoWithId() {
    // Given
    when(modelMapper.map(editionDtoToAdd, Edition.class)).thenReturn(editionToAdd);
    editionToAdd.setId(idToAdd);
    when(editionRepository.save(editionToAdd)).thenReturn(editionToAdd);
    editionDtoToAdd.setId(idToAdd);
    when(modelMapper.map(editionToAdd, EditionDto.class)).thenReturn(editionDtoToAdd);

    // When
    EditionDto result = editionService.add(editionDtoToAdd);

    // Then
    assertAll(
            () -> assertSame(editionDtoToAdd, result, "expected Object and result Object are the same"),
            () -> assertNotNull(result.getId(), "result EditionDto has an ID"),
            () -> assertEquals(idToAdd, result.getId(), "expected EditionDto ID and result EditionDto ID are equal"),
            () -> verify(editionRepository).save(editionToAdd),
            () -> verify(modelMapper).map(editionDtoToAdd, Edition.class),
            () -> verify(modelMapper).map(editionToAdd, EditionDto.class)
    );
  }
}
