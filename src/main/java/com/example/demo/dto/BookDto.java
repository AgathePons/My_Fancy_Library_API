package com.example.demo.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class BookDto {
    private Long id;
    private String title;
    private String abstractText;
    private int yearOfRelease;
    private String cover;
    private String author;
    private String edition;
    private List<String> categories;
}
