package com.example.week3.Repository;

import com.example.week3.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByNameAndId(String name,Long id);
}
