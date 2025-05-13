package com.example.demo.service.impl;

import com.example.demo.dto.BookDto;
import com.example.demo.dto.BookFullDto;
import com.example.demo.dto.dtomapper.BookDtoMapper;
import com.example.demo.entities.Author;
import com.example.demo.entities.Book;
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
  static final String ITEM_EDITioN = "edition";
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

    if (bookFullDto.getEdition() != null && bookFullDto.getEdition().getId() != null) {
      var editionEntity = editionRepository.findById(bookFullDto.getEdition().getId())
              .orElseThrow(() ->
                      new EntityNotFoundException("Edition with ID " + bookFullDto.getEdition().getId() + " not found")
              );
      bookEntity.setEdition(editionEntity);
    }

    if (bookFullDto.getAuthor() != null && bookFullDto.getAuthor().getId() != null) {
      var authorEntity = authorRepository.findById(bookFullDto.getAuthor().getId())
              .orElseThrow(() ->
                      new EntityNotFoundException("Author with ID " + bookFullDto.getAuthor().getId() + " not found")
              );
      bookEntity.setAuthor(authorEntity);
    }

//    if (bookFullDto.getAuthor() == null || bookFullDto.getAuthor().getId() == null) {
//      throw new NoDataFoundError("Author ID is missing");
//    }
//    Author author = authorRepository.findById(bookFullDto.getAuthor().getId())
//            .orElseThrow(() -> NoDataFoundError.withId(ITEM_AUTHOR, bookFullDto.getAuthor().getId()));
//    bookEntity.setAuthor(author);

    if (bookFullDto.getCategories() != null) {
      var categoriesEntity = bookFullDto.getCategories().stream()
              .map(categoryDto -> categoryRepository.findById(categoryDto.getId())
                      .orElseThrow(
                              () -> new EntityNotFoundException("Category with ID " + categoryDto.getId() + " not found")
                      ))
              .toList();
      bookEntity.setCategories(categoriesEntity);

    }

    this.bookRepository.save(bookEntity);
    return  modelMapper.map(bookEntity, BookFullDto.class);
  }
}
