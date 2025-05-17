package com.example.demo.controller;

import com.example.demo.dto.AuthorDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.AuthorService;
import jakarta.validation.Valid;
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
        logger.info("getAllAuthor: {}", ITEM_TYPE);
        return authorService.findAll();
    }

    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable("id") long id) {
        logger.info("getById : {} ID {}", ITEM_TYPE, id);
        return authorService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError : {} ID {}", ITEM_TYPE, id);
            return NoDataFoundError.withId(ITEM_TYPE,id);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto add(@Valid @RequestBody AuthorDto authorDto) {
        logger.info("add: {}", ITEM_TYPE);
        return authorService.add(authorDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public AuthorDto update(@Valid @RequestBody AuthorDto authorDto) {
        logger.info("update: {}", ITEM_TYPE);
        return authorService.update(authorDto)
                .orElseThrow(() -> NoDataFoundError.withId(ITEM_TYPE, authorDto.getId()));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) {
        logger.info("deleteById: {}", ITEM_TYPE);
        if (!authorService.delete(id)) {
            throw NoDataFoundError.withId(ITEM_TYPE, id);
        }
    }
}
