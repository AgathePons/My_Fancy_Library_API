package com.example.demo.service.impl;

import com.example.demo.dto.CategoryDto;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import org.modelmapper.ModelMapper;
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

    @Override
    public List<CategoryDto> findAll() {
        return ModelMapperUtil.mapList(categoryRepository.findAll(), CategoryDto.class);
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        return categoryRepository.findById(id).map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDto.class));
    }
}
