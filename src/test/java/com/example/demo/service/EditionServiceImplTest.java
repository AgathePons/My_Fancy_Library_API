package com.example.demo.service;

import com.example.demo.dto.EditionDto;
import com.example.demo.entities.Edition;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.EditionRepository;
import com.example.demo.service.impl.EditionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EditionServiceImplTest {
  @InjectMocks
  private EditionServiceImpl editionService;

  @Mock
  private EditionRepository editionRepository;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private ModelMapper modelMapper;

  @Test
  void findAll_shouldReturnListOfEditionDto() {
    List<Edition> editions = List.of(
      Edition.builder().id(1L).name("edition1").build(),
      Edition.builder().id(2L).name("edition2").build()
    );

    List<EditionDto> editionsDto = List.of(
      EditionDto.builder().id(1L).name("edition1").build(),
      EditionDto.builder().id(2L).name("edition2").build()
    );

    when(editionRepository.findAll()).thenReturn(editions);


  }
}
