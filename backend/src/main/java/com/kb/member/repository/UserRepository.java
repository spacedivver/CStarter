package com.kb.member.repository;

import com.kb.member.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email); // email 필드를 기준으로 중복 여부 확인
}
