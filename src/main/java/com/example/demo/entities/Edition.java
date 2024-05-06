package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="edition")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
}
