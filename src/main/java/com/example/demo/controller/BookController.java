package com.example.demo.controller;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookFullDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        logger.info("getAllBook");
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public BookDto getById(@PathVariable("id") long id) {
        logger.info("getById : " + id);
        return bookService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError");
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }

    @GetMapping("full")
    public List<BookFullDto> getAllBookFull() {
        logger.info("getAllBookFull");
        return bookService.findAllFull();
    }

    @GetMapping("full/{id}")
    public BookFullDto getByIdFull(@PathVariable("id") long id) {
        logger.info("getByIdFull : " + id);
        return bookService.findByIdFull(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError");
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }
}
