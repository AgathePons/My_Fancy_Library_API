package com.example.demo.service.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookFullDto;
import com.example.demo.modelmapper.BookMapper;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements com.example.demo.service.BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<BookDto> findAll() {
        bookRepository.findAll();
        return ModelMapperUtil.mapListBookMapper(bookRepository.findAll(), bookMapper);
    }

    @Override
    public List<BookFullDto> findAllFull() {
        return ModelMapperUtil.mapList(bookRepository.findAll(), BookFullDto.class);
    }

    @Override
    public Optional<BookDto> findById(Long id) {
        return bookRepository.findById(id).map(bookEntity -> bookMapper.convertToDto(bookEntity));
    }

    @Override
    public Optional<BookFullDto> findByIdFull(Long id) {
        return bookRepository.findById(id).map(bookEntity -> modelMapper.map(bookEntity, BookFullDto.class));
    }
}
