package com.example.library.repository;

import com.example.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Репозиторий для работы с сущностью Author.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE authors_author_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}