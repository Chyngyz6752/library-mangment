package com.example.library.repository;

import com.example.library.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Репозиторий читателей (поиск по email).
 */

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Page<Member> findByActive(boolean active, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE members_member_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}
