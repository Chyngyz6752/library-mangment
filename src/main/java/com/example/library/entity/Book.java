package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Сущность, представляющая книгу в библиотеке.
 */
@Entity
@Table(name = "books", indexes = {
        @Index(name = "idx_book_title", columnList = "title")
}, uniqueConstraints = {
        @UniqueConstraint(columnNames = "isbn")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String title;

    private String subtitle;

    @Column(unique = true)
    private String isbn;

    private String publisher;

    private int publishYear;

    private String language;

    private int pages;

    private int totalCopies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}