package com.example.demo.controller;

import com.example.demo.dto.EditionDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.EditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("edition")
public class EditionController {

    static final String ITEM_TYPE = "Edition";
    Logger logger = LoggerFactory.getLogger(EditionController.class);
    @Autowired
    private EditionService editionService;

    @GetMapping
    public List<EditionDto> getAllEdition() {
        logger.info("getAllAuthor");
        return editionService.findAll();
    }

    @GetMapping("{id}")
    public EditionDto getById(@PathVariable("id") long id) {
        logger.info("getById : " + id);
        return editionService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError");
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }
}
