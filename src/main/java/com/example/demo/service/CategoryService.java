package com.example.demo.service;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryWithBooksDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDto> findAll();
    Optional<CategoryDto> findById(Long id);
    List<CategoryWithBooksDto> findAllWithBooks();
}
