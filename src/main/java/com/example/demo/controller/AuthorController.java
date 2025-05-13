package com.example.demo.controller;

import com.example.demo.dto.AuthorDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {

    static final String ITEM_TYPE = "Author";
    Logger logger = LoggerFactory.getLogger(AuthorController.class);
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<AuthorDto> getAllAuthor() {
        logger.info("getAllAuthor");
        return authorService.findAll();
    }

    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable("id") long id) {
        logger.info("getById : " + id);
        return authorService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError");
            return NoDataFoundError.withId(ITEM_TYPE,id);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto add(@RequestBody AuthorDto authorDto) {
        return authorService.add(authorDto);
    }
}
