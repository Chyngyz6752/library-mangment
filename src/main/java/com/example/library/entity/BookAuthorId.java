package com.example.library.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

/**
 * Составной ключ для сущности BookAuthor.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAuthorId implements Serializable {
    private Long bookId;
    private Long authorId;
}