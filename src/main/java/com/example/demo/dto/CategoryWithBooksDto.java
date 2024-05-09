package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryWithBooksDto {
    private Long id;
    private String name;
    private List<BookDto> books;
}
