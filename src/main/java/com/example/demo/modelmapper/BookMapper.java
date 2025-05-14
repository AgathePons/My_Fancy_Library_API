package com.example.demo.modelmapper;

import com.example.demo.dto.BookDto;
import com.example.demo.entities.Book;
import com.example.demo.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookDto convertToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);
        bookDto.setAuthor(book.getAuthor().getFirstname() + " " + book.getAuthor().getLastname());
        bookDto.setEdition(book.getEdition().getName());
        bookDto.setCategories(book.getCategories().stream().map(Category::getName).toList());
        return bookDto;
    }
}
