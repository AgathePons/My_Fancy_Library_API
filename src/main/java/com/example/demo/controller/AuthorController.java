package com.example.demo.controller;

import com.example.demo.dto.AuthorDto;
import com.example.demo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("{id}")
    public AuthorDto getById(@PathVariable("id") long id) {
        return authorService.findById(id).orElseThrow();
    }
}
