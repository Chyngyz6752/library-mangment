package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.entity.BookCopy;
import com.example.library.enums.BookCopyStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from BookCopy c where c.copyId = :id")
    Optional<BookCopy> findByIdForUpdate(@Param("id") Long id);

    List<BookCopy> findByBook(Book book);

    long countByBookAndStatus(Book book, BookCopyStatus status);
}
