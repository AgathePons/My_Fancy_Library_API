package com.example.demo.service.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookFullDto;
import com.example.demo.dto.dtomapper.BookDtoMapper;
import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.entities.Category;
import com.example.demo.entities.Edition;
import com.example.demo.error.BadRequestError;
import com.example.demo.modelmapper.BookMapper;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.EditionRepository;
import com.example.demo.service.BookService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

  @Autowired
  private AuthorRepository authorRepository;

  @Autowired
  private EditionRepository editionRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private BookMapper bookMapper;

  @Autowired
  private BookDtoMapper bookDtoMapper;

  Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

  static final String ITEM_TYPE = "book";
  static final String ITEM_AUTHOR = "author";
  static final String ITEM_EDITION = "edition";
  static final String ITEM_CATEGORY = "category";
  static final String WITH_ID_NOT_FOUND = "{} with id {} not found";
  static final String ID_IS_MISSING = " id is missing";


  @Override
  public List<BookDto> findAll() {
    Iterable<Book> booksIterable = bookRepository.findAll();
    return bookDtoMapper.convert(booksIterable);
  }

  @Override
  public List<BookFullDto> findAllFull() {
    return ModelMapperUtil.mapList(bookRepository.findAll(), BookFullDto.class);
  }

  @Override
  public Optional<BookDto> findById(Long id) {
    return bookRepository.findById(id).map(bookEntity -> bookMapper.convertToDto(bookEntity));
  }

  @Override
  public Optional<BookFullDto> findByIdFull(Long id) {
    return bookRepository.findById(id).map(bookEntity -> modelMapper.map(bookEntity, BookFullDto.class));
  }

  @Override
  public BookFullDto add(BookFullDto bookFullDto) {
    var bookEntity = modelMapper.map(bookFullDto, Book.class);

    // Author
    if (bookFullDto.getAuthor().getId() == null) {
      logger.warn(ITEM_AUTHOR + ID_IS_MISSING);
      throw BadRequestError.missingFieldId(ITEM_AUTHOR);
    }
    Author author = authorRepository.findById(bookFullDto.getAuthor().getId())
            .orElseThrow(() -> {
              logger.warn(WITH_ID_NOT_FOUND, ITEM_AUTHOR, bookFullDto.getAuthor().getId());
              return BadRequestError.withId(ITEM_AUTHOR, bookFullDto.getAuthor().getId());
            });
    bookEntity.setAuthor(author);

    // Edition
    if (bookFullDto.getEdition().getId() == null) {
      logger.warn(ITEM_EDITION + ID_IS_MISSING);
      throw BadRequestError.missingFieldId(ITEM_EDITION);
    }
    Edition edition = editionRepository.findById(bookFullDto.getEdition().getId())
            .orElseThrow(() -> {
              logger.warn(WITH_ID_NOT_FOUND, ITEM_EDITION, bookFullDto.getEdition().getId());
              return BadRequestError.withId(ITEM_EDITION, bookFullDto.getEdition().getId());
            });
    bookEntity.setEdition(edition);

    // Categories
    if (bookFullDto.getCategories() != null && !bookFullDto.getCategories().isEmpty()) {
      List<Category> categories = bookFullDto.getCategories().stream()
              .filter(categoryDto -> categoryDto.getId() != null)
              .map(categoryDto ->
                      categoryRepository.findById(categoryDto.getId())
                              .orElseThrow(() -> {
                                logger.warn(WITH_ID_NOT_FOUND, ITEM_CATEGORY, categoryDto.getId());
                                return BadRequestError.withId(ITEM_CATEGORY, categoryDto.getId());
                              })
              )
              .toList();
      bookEntity.setCategories(categories);
    } else {
      bookEntity.setCategories(Collections.emptyList());
    }

    // Save
    this.bookRepository.save(bookEntity);
    return  modelMapper.map(bookEntity, BookFullDto.class);
  }

  @Override
  public Optional<BookFullDto> update(BookFullDto bookFullDto) {
    if (bookFullDto.getId() == null) {
      throw BadRequestError.missingId(ITEM_TYPE);
    }

    var bookEntity = modelMapper.map(bookFullDto, Book.class);

    // Author
    if (bookFullDto.getAuthor().getId() == null) {
      logger.warn(ITEM_AUTHOR + ID_IS_MISSING);
      throw BadRequestError.missingFieldId(ITEM_AUTHOR);
    }
    Author author = authorRepository.findById(bookFullDto.getAuthor().getId())
            .orElseThrow(() -> {
              logger.warn(WITH_ID_NOT_FOUND, ITEM_AUTHOR, bookFullDto.getAuthor().getId());
              return BadRequestError.withId(ITEM_AUTHOR, bookFullDto.getAuthor().getId());
            });
    bookEntity.setAuthor(author);

    // Edition
    if (bookFullDto.getEdition().getId() == null) {
      logger.warn(ITEM_EDITION + ID_IS_MISSING);
      throw BadRequestError.missingFieldId(ITEM_EDITION);
    }
    Edition edition = editionRepository.findById(bookFullDto.getEdition().getId())
            .orElseThrow(() -> {
              logger.warn(WITH_ID_NOT_FOUND, ITEM_EDITION, bookFullDto.getEdition().getId());
              return BadRequestError.withId(ITEM_EDITION, bookFullDto.getEdition().getId());
            });
    bookEntity.setEdition(edition);

    // Categories
    if (bookFullDto.getCategories() != null && !bookFullDto.getCategories().isEmpty()) {
      List<Category> categories = bookFullDto.getCategories().stream()
              .filter(categoryDto -> categoryDto.getId() != null)
              .map(categoryDto ->
                      categoryRepository.findById(categoryDto.getId())
                              .orElseThrow(() -> {
                                logger.warn(WITH_ID_NOT_FOUND, ITEM_CATEGORY, categoryDto.getId());
                                return BadRequestError.withId(ITEM_CATEGORY, categoryDto.getId());
                              }))

              .toList();
      bookEntity.setCategories(categories);
    } else {
      bookEntity.setCategories(Collections.emptyList());
    }

    // Save
    this.bookRepository.save(bookEntity);
    return Optional.of(modelMapper.map(bookEntity, BookFullDto.class));
  }

  @Override
  public boolean delete(long id) {
    if (!bookRepository.existsById(id)) {
      logger.warn(WITH_ID_NOT_FOUND, ITEM_TYPE);
      return false;
    }

    return bookRepository.findById(id)
            .map(bookEntity -> {
              bookRepository.delete(bookEntity);
              return true;
            })
            .orElse(false);
  }
}
