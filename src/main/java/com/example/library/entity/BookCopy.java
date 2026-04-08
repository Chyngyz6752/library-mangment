package com.example.library.entity;

import com.example.library.enums.BookCopyStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Сущность, представляющая конкретный экземпляр книги.
 */
@Entity
@Table(name = "book_copies", uniqueConstraints = {
        @UniqueConstraint(columnNames = "barcode")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long copyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(unique = true, nullable = false)
    private String barcode;

    private String accessionNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookCopyStatus status;

    private String location;

    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt = LocalDateTime.now();
}