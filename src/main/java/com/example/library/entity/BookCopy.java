package com.example.library.entity;

import com.example.library.enums.BookCopyStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "book_copies", uniqueConstraints = {
        @UniqueConstraint(name = "uk_book_copy_barcode", columnNames = "barcode")
}, indexes = {
        @Index(name = "idx_copy_book", columnList = "book_id"),
        @Index(name = "idx_copy_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long copyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(unique = true, nullable = false)
    private String barcode;

    @Column(name = "accession_number")
    private String accessionNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookCopyStatus status;

    private String location;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime addedAt;

    @Version
    private Long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopy other)) return false;
        return copyId != null && copyId.equals(other.copyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(copyId);
    }
}
