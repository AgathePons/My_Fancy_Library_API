package com.example.demo.service.impl;

import com.example.demo.dto.EditionDto;
import com.example.demo.entities.Edition;
import com.example.demo.error.BadRequestError;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.EditionRepository;
import com.example.demo.service.EditionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditionServiceImpl implements EditionService {

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(EditionServiceImpl.class);

    static final String ITEM_TYPE = "edition";

    static final String WITH_ID_NOT_FOUND = "{} with id {} not found";

    @Override
    public List<EditionDto> findAll() {
        return ModelMapperUtil.mapList(editionRepository.findAll(), EditionDto.class);
    }

    @Override
    public Optional<EditionDto> findById(Long id) {
        return editionRepository.findById(id).map(editionEntity -> modelMapper.map(editionEntity, EditionDto.class));
    }

    @Override
    public EditionDto add(EditionDto editionDto) {
        var editionEntity = modelMapper.map(editionDto, Edition.class);
        this.editionRepository.save(editionEntity);
        return modelMapper.map(editionEntity, EditionDto.class);
    }

    @Override
    public boolean delete(long id) {
        if (!editionRepository.existsById(id)) {
            logger.warn(WITH_ID_NOT_FOUND, ITEM_TYPE);
            return false;
        }

        var numberOfBookLinked = bookRepository.countByEditionId(id);
        if (numberOfBookLinked > 0) {
            throw BadRequestError.deletionNotAllowedLinkedItem(ITEM_TYPE, "book", numberOfBookLinked);
        }

        return editionRepository.findById(id)
                .map(editionEntity -> {
                    editionRepository.delete(editionEntity);
                    return true;
                })
                .orElse(false);
    }
}
