package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookFullDto {
    private Long id;
    private String title;
    private String abstractText;
    private int yearOfRelease;
    private String cover;
    private AuthorDto author;
    private EditionDto edition;
    private List<CategoryDto> categories;
}
