package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="book")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "abstract", nullable = false)
    private String abstractText;

    @Column(name = "year", nullable = false)
    private int yearOfRelease;

    @Column(name = "cover", nullable = false)
    private String cover;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id")
    private Edition edition;
}
