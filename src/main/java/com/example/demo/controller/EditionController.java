package com.example.demo.controller;

import com.example.demo.dto.EditionDto;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.service.EditionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        logger.info("getAllEdition: {}", ITEM_TYPE);
        return editionService.findAll();
    }

    @GetMapping("{id}")
    public EditionDto getById(@PathVariable("id") long id) {
        logger.info("getById : {}", id);
        return editionService.findById(id).orElseThrow(() -> {
            logger.warn("NoDataFoundError : {} ID {}", ITEM_TYPE, id);
            return NoDataFoundError.withId(ITEM_TYPE, id);
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditionDto add(@Valid @RequestBody EditionDto editionDto) {
        logger.info("add: {}", ITEM_TYPE);
        return editionService.add(editionDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public EditionDto update(@Valid @RequestBody EditionDto editionDto) {
        logger.info("update: {}", ITEM_TYPE);
        return editionService.update(editionDto)
                .orElseThrow(() -> NoDataFoundError.withId(ITEM_TYPE, editionDto.getId()));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) {
        logger.info("deleteById: {}", ITEM_TYPE);
        if (!editionService.delete(id)) {
            throw NoDataFoundError.withId(ITEM_TYPE, id);
        }
    }
}
