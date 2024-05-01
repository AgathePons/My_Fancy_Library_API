package com.example.demo.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AuthorDto {
    private Long id;
    private String firstname;
    private String lastname;
}
