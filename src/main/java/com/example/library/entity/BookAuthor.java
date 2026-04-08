package com.example.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Сущность для связи многие-ко-многим между Book и Author.
 */
@Entity
@Table(name = "book_authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthor {
    @EmbeddedId
    private BookAuthorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    private String contribution; // e.g., "Author", "Illustrator"
}