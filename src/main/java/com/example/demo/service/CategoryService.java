package com.example.demo.service;

import com.example.demo.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> findAll();
    Optional<CategoryDto> findById(Long id);
}
