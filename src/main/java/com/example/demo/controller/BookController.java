package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookFullDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    static final String ITEM_TYPE = "Book";
    Logger logger = LoggerFactory.getLogger(BookController.class);
    @Autowired
    private BookService bookService;

    @GetMapping
    public List<BookDto> getAllBook() {
        logger.info("getAllBook: {}", ITEM_TYPE);
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public BookDto getById(@PathVariable("id") long id) {
        logger.info("getById : {} ID {}", ITEM_TYPE, id);
        return bookService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError : {} ID {}", ITEM_TYPE, id);
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }

    @GetMapping("full")
    public List<BookFullDto> getAllBookFull() {
        logger.info("getAllBookFull: " + ITEM_TYPE);
        return bookService.findAllFull();
    }

    @GetMapping("full/{id}")
    public BookFullDto getByIdFull(@PathVariable("id") long id) {
        logger.info("getByIdFull: {} ID {}", ITEM_TYPE, id);
        return bookService.findByIdFull(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError: {} ID {}", ITEM_TYPE, id);
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookFullDto add(@Valid @RequestBody BookFullDto bookFullDto) {
        logger.info("add: {}", ITEM_TYPE);
        return bookService.add(bookFullDto);
    }
}
