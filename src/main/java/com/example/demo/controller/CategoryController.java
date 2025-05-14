package com.example.demo.controller;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryWithBooksDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    static final String ITEM_TYPE = "Category";

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAllCategory() {
        logger.info("getAllCategory: {}", ITEM_TYPE);
        return categoryService.findAll();
    }

    @GetMapping("{id}")
    public CategoryDto getById(@PathVariable("id") long id) {
        logger.info("getById : {} ID {}", ITEM_TYPE, id);
        return categoryService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError : {} ID {}", ITEM_TYPE, id);
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }

    @GetMapping("/withBooks")
    public List<CategoryWithBooksDto> getAllCategoryWithBooks() {
        logger.info("getAllCategoryWithBooks: {}", ITEM_TYPE);
        return categoryService.findAllWithBooks();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto add(@Valid @RequestBody CategoryDto categoryDto) {
        logger.info("add: {}", ITEM_TYPE);
        return categoryService.add(categoryDto);
    }

    //TODO update

    //TODO delete
}
