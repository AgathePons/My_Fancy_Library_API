package com.example.demo.service.impl;

import com.example.demo.dto.AuthorDto;
import com.example.demo.entities.Author;
import com.example.demo.error.BadRequestError;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.AuthorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

    static final String ITEM_TYPE = "author";
    static final String WITH_ID_NOT_FOUND = "{} with id {} not found";

    @Override
    public List<AuthorDto> findAll() {
        return ModelMapperUtil.mapList(authorRepository.findAll(), AuthorDto.class);
    }

    @Override
    public Optional<AuthorDto> findById(Long id) {
        return authorRepository.findById(id).map(authorEntity -> modelMapper.map(authorEntity, AuthorDto.class));
    }

    @Override
    public AuthorDto add(AuthorDto authorDto) {
        var authorEntity = modelMapper.map(authorDto, Author.class);
        this.authorRepository.save(authorEntity);
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public boolean delete(long id) {
        if (!authorRepository.existsById(id)) {
            logger.warn(WITH_ID_NOT_FOUND, ITEM_TYPE);
            return false;
        }

        var numberOfBookLinked = bookRepository.countByAuthorId(id);
        if (numberOfBookLinked > 0) {
            throw BadRequestError.deletionNotAllowedLinkedItem(ITEM_TYPE, "book", numberOfBookLinked);
        }

        return authorRepository.findById(id)
                .map(authorEntity -> {
                    authorRepository.delete(authorEntity);
                    return true;
                })
                .orElse(false);
    }
}
