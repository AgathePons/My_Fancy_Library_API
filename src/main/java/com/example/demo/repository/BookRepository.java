package com.example.demo.repository;

import com.example.demo.entities.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
  int countByAuthorId(Long authorId);
  int countByEditionId(Long editionId);
}
