package com.example.demo.controller;

import com.example.demo.dto.AuthorDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {

    static final String ITEM_TYPE = "Author";
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<AuthorDto> getAllAuthor() {
        return authorService.findAll();
    }

    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable("id") long id) {
        return authorService.findById(id).orElseThrow(() -> NoDataFoundError.withId(ITEM_TYPE,id));
    }
}
