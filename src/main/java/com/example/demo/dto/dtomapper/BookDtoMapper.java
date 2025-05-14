package com.example.demo.dto.dtomapper;

import com.example.demo.dto.BookDto;
import com.example.demo.entities.Book;
import com.example.demo.modelmapper.BookMapper;
import com.example.demo.modelmapper.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDtoMapper {

    @Autowired
    private BookMapper bookMapper;

    public List<BookDto> convert(Iterable<Book> booksIterable) {
        List<Book> bookList = ModelMapperUtil.mapList(booksIterable, Book.class);
        return bookList.stream().map(book -> bookMapper.convertToDto(book)).toList();
    }
}
