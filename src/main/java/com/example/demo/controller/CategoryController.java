package com.example.demo.controller;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryWithBooksDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        logger.info("getAllCategory");
        return categoryService.findAll();
    }

    @GetMapping("{id}")
    public CategoryDto getById(@PathVariable("id") long id) {
        logger.info("getById : " + id);
        return categoryService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError");
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }

    @GetMapping("/withBooks")
    public List<CategoryWithBooksDto> getAllCategoryWithBooks() {
        logger.info("getAllCategoryWithBooks");
        return categoryService.findAllWithBooks();
    }
}
