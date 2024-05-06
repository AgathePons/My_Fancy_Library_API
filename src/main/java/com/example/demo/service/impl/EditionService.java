package com.example.demo.service.impl;

import com.example.demo.dto.EditionDto;
import com.example.demo.modelMapper.ModelMapperUtil;
import com.example.demo.repository.EditionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditionService implements com.example.demo.service.EditionService {

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<EditionDto> findAll() {
        return ModelMapperUtil.mapList(editionRepository.findAll(), EditionDto.class);
    }

    @Override
    public Optional<EditionDto> findById(Long id) {
        return editionRepository.findById(id).map(editionEntity -> modelMapper.map(editionEntity, EditionDto.class));
    }
}
