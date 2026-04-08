package com.example.library.repository;

import com.example.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью Member.
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}