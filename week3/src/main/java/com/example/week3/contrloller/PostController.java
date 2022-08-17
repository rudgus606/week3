package com.example.week3.contrloller;

import com.example.week3.dto.PasswordDto;
import com.example.week3.dto.PostRequestDto;
import com.example.week3.entity.Post;
import com.example.week3.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    // 글 작성
    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto) {
        return postService.create(requestDto);
    }

    // 비밀번호 확인
    @PostMapping("/api/auth/{id}")
    public boolean isValidPassword(@PathVariable Long id, @RequestBody PasswordDto requestDto) {
        return postService.chkPassword(id, requestDto);
    }

    // 글 수정
    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.update(id, requestDto);
    }

    // 글 삭제
    @DeleteMapping("/api/posts/{id}")
    public Long deletePost(@PathVariable Long id) {
        postService.delete(id);
        return id;
    }

    // 전체 조회
    @GetMapping("/api/posts")
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    // 글 조회
    @GetMapping("/api/posts/{id}")
    public Post getDetailPosts(@PathVariable Long id) {
        return postService.getDetailPost(id);
    }
}