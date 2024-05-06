package com.example.demo.repository;

import com.example.demo.entities.Edition;
import org.springframework.data.repository.CrudRepository;

public interface EditionRepository extends CrudRepository<Edition, Long> {
}
