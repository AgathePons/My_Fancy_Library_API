package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditionDto {

    private Long id;

    @NotBlank(message = "name is mandatory")
    private String name;
}
