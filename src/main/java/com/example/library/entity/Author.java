package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Сущность, представляющая автора книги.
 */
@Entity
@Table(name = "authors", uniqueConstraints = {
    @UniqueConstraint(columnNames = "fullName")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(nullable = false, unique = true)
    private String fullName;

    private Integer birthYear;
    private Integer deathYear;

    @Column(columnDefinition = "TEXT")
    private String biography;
}