package com.example.library.repository;

import com.example.library.entity.Book;
import com.example.library.entity.BookCopy;
import com.example.library.enums.BookCopyStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from BookCopy c where c.copyId = :id")
    Optional<BookCopy> findByIdForUpdate(@Param("id") Long id);

    List<BookCopy> findByBook(Book book);

    long countByBookAndStatus(Book book, BookCopyStatus status);

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE book_copies_copy_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}
