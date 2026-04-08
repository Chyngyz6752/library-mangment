package com.example.library.repository;

import com.example.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью Author.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}