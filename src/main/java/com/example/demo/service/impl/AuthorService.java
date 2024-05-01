package com.example.demo.service.impl;

import com.example.demo.dto.AuthorDto;
import com.example.demo.repository.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService implements com.example.demo.service.AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<AuthorDto> findAll() {
        return null;
    }

    @Override
    public Optional<AuthorDto> findById(Long id) {
        return this.authorRepository.findById(id).map(authorEntity -> modelMapper.map(authorEntity, AuthorDto.class));
    }
}
