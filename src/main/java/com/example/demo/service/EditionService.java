package com.example.demo.service;

import com.example.demo.dto.EditionDto;

import java.util.List;
import java.util.Optional;

public interface EditionService {
    List<EditionDto> findAll();
    Optional<EditionDto> findById(Long id);
    EditionDto add(EditionDto editionDto);
    boolean delete(long id);
}
