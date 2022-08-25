package com.example.week3.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import com.example.week3.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByNickname(String nickname);

    boolean existsByNickname(String nickname);
}