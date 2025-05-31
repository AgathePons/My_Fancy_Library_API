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

  @Test
  @DisplayName("findAll should return a list of EditionDto")
  void findAll_shouldReturnEditionDtoList() {
    // Given
    when(editionRepository.findAll()).thenReturn(editionList);
    try(MockedStatic<ModelMapperUtil> mockedStatic = mockStatic(ModelMapperUtil.class)) {
      mockedStatic.when(() -> ModelMapperUtil.mapList(editionList, EditionDto.class)).thenReturn(editionDtoList);
    }

    // When
    List<EditionDto> result = editionService.findAll();
    System.out.println("result : " + result.toString());
    System.out.println(result.size() + " elements in the result list");
    for (EditionDto editionDto : result) {
      System.out.println("\t" + editionDto.getName() + " id = " + editionDto.getId());
    }
    System.out.println("expected : " + editionDtoList.toString());
    System.out.println(editionDtoList.size() + " elements in the editionDtoList");
    for (EditionDto editionDto : editionDtoList) {
      System.out.println("\t" + editionDto.getName() + " id = " + editionDto.getId());
    }

    System.out.println("editionDtoList[0] == result[0] ? " + (editionDtoList.get(0) == result.get(0)));
    System.out.println("editionDtoList[1] == result[1] ? " + (editionDtoList.get(1) == result.get(1)));

    // Then
    assertAll(
            "verify findAll flow and result",
            () -> assertNotNull(result, "result is not null"),
            () -> assertEquals(2, result.size(), "list should have 2 elements"),
            () -> assertIterableEquals(editionDtoList, result, "expected list and result list are equal Iterable"),
            () -> assertEquals(editionDtoList, result, "expected list should match result"),
            () -> assertSame(editionDtoList, result, "expected list and result should be the same object")
    );
  }

  @Test
  @DisplayName("test if Iterable are equals")
  void testIterableEquals() {
    List<EditionDto> resultEditionDtoList = editionDtoList;
    assertIterableEquals(editionDtoList, resultEditionDtoList, "editionDtoList and resultEditionDtoList are same Iterable when resultEditionDtoList = editionDtoList");

    when(editionRepository.findAll()).thenReturn(editionList);
    Iterable<Edition> resultEditionList = editionRepository.findAll();
    assertIterableEquals(editionList, resultEditionList, "editionList and resultEditionList are same Iterable when editionRepository.findAll returns editionList");


    try(MockedStatic<ModelMapperUtil> mockedStatic = mockStatic(ModelMapperUtil.class)) {
//      mockedStatic.when(() -> ModelMapperUtil.mapList(editionList, EditionDto.class)).thenAnswer(invocation -> {
//        System.out.println(">>> MOCK called with: " + invocation.getArguments()[0]);
//        return resultEditionDtoList;
//      });
      mockedStatic.when(ModelMapperUtil::getSomething).thenReturn("GnihihihahHAHohoHö");

      mockedStatic.when(() -> ModelMapperUtil.mapList(resultEditionList, EditionDto.class))
              .thenReturn(resultEditionDtoList);

      List<EditionDto> result;
      EditionService editionServiceInTry = new EditionServiceImpl(editionRepository);
      result = editionServiceInTry.findAll();
      System.out.println("result size : " + result.size());
      System.out.println("result to string : " + result.toString());

      assertIterableEquals(resultEditionDtoList, result, "resultEditionDtoList and result are same Iterable when ModelMapperUtil.mapList returns resultEditionDtoList");
    } catch (Exception e) {
      System.out.println(">>>>> in catch");
    }

  }
}
