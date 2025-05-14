package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AuthorDto {

    private Long id;

    @NotBlank(message = "firstname is mandatory")
    private String firstname;

    @NotBlank(message = "lastname is mandatory")
    private String lastname;
}
