package com.example.library.repository;

import com.example.library.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью BookCopy.
 */
@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
}