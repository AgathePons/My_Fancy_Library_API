package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookFullDto {
    
    private Long id;

    @NotBlank(message = "title is mandatory")
    private String title;

    @NotBlank(message = "abstract text is mandatory")
    private String abstractText;

    @NotNull(message = "year of release is mandatory")
    @Min(value = 0, message = "year of release muse be positive")
    private Integer yearOfRelease;

    @NotBlank(message = "cover is mandatory")
    private String cover;

    @NotNull(message = "author is mandatory")
    private AuthorDto author;

    @NotNull(message = "edition is mandatory")
    private EditionDto edition;

    private List<CategoryDto> categories;
}
