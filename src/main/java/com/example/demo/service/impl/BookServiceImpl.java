package com.example.demo.service.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookFullDto;
import com.example.demo.dto.dtomapper.BookDtoMapper;
import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
import com.example.demo.entities.Category;
import com.example.demo.entities.Edition;
import com.example.demo.error.NoDataFoundError;
import com.example.demo.modelmapper.BookMapper;
import com.example.demo.modelmapper.ModelMapperUtil;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.EditionRepository;
import com.example.demo.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  static final String ITEM_BOOK = "book";
  static final String ITEM_AUTHOR = "author";
  static final String ITEM_EDITION = "edition";
  static final String ITEM_CATEGORY = "category";


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
    if (bookFullDto.getAuthor() == null || bookFullDto.getAuthor().getId() == null) {
      throw new NoDataFoundError("Author ID is missing");
    }
    Author author = authorRepository.findById(bookFullDto.getAuthor().getId())
            .orElseThrow(() -> NoDataFoundError.withId(ITEM_AUTHOR, bookFullDto.getAuthor().getId()));
    bookEntity.setAuthor(author);

    // Edition
    if (bookFullDto.getEdition() == null || bookFullDto.getEdition().getId() == null) {
      throw new NoDataFoundError("Edition ID is missing");
    }
    Edition edition = editionRepository.findById(bookFullDto.getEdition().getId())
            .orElseThrow(() -> NoDataFoundError.withId(ITEM_EDITION, bookFullDto.getEdition().getId()));
    bookEntity.setEdition(edition);

    // Categories
    if (bookFullDto.getCategories() != null && !bookFullDto.getCategories().isEmpty()) {
      List<Category> categories = bookFullDto.getCategories().stream()
              .filter(categoryDto -> categoryDto.getId() != null)
              .map(categoryDto ->
                      categoryRepository.findById(categoryDto.getId())
                              .orElseThrow(() -> NoDataFoundError.withId(ITEM_CATEGORY, categoryDto.getId()))
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
}
