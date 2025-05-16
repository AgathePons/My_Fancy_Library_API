package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookFullDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> findAll();
    List<BookFullDto> findAllFull();
    Optional<BookDto> findById(Long id);
    Optional<BookFullDto> findByIdFull(Long id);
    BookFullDto add(BookFullDto bookFullDto);
    boolean delete(long id);
}
