package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDto;
import com.example.demo.dto.CategoryWithBooksDto;
import com.example.demo.dto.dtomapper.CategoryWithBooksDtoMapper;
import com.example.demo.entities.Category;
import com.example.demo.error.BadRequestError;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryWithBooksDtoMapper categoryWithBooksDtoMapper;

    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    static final String ITEM_TYPE = "category";
    static final String WITH_ID_NOT_FOUND = "{} with id {} not found";

    @Override
    public List<CategoryDto> findAll() {
        return ModelMapperUtil.mapList(categoryRepository.findAll(), CategoryDto.class);
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id).map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class));
    }

    @Override
    public List<CategoryWithBooksDto> findAllWithBooks() {
        Iterable<Category> categoriesIterable = categoryRepository.findAll();
        return categoryWithBooksDtoMapper.convert(categoriesIterable);
    }

    @Override
    public CategoryDto add(CategoryDto categoryDto) {
        var categoryEntity = modelMapper.map(categoryDto, Category.class);
        this.categoryRepository.save(categoryEntity);
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public Optional<CategoryDto> update(CategoryDto categoryDto) {
        if (categoryDto.getId() == null) {
            throw BadRequestError.missingId(ITEM_TYPE);
        }
        return categoryRepository.findById(categoryDto.getId()).map(categoryEntity -> {
            modelMapper.map(categoryDto, categoryEntity);
            categoryRepository.save(categoryEntity);
            return modelMapper.map(categoryEntity, CategoryDto.class);
        });
    }

    @Override
    public boolean delete(long id) {
        if (!categoryRepository.existsById(id)) {
            logger.warn(WITH_ID_NOT_FOUND, ITEM_TYPE);
            return false;
        }

        return categoryRepository.findById(id)
                .map(categoryEntity -> {
                    categoryRepository.delete(categoryEntity);
                    return true;
                })
                .orElse(false);
    }
}
