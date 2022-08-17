package com.example.week3.service;

import com.example.week3.dto.PasswordDto;
import com.example.week3.dto.PostRequestDto;
import com.example.week3.entity.Post;
import com.example.week3.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Setter
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //글업데이트
    @Transactional
    public Post update(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        post.update(requestDto);
        return post;
    }

    //글저장
    @Transactional
    public Post create(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;
    }

    //글삭제
    @Transactional
    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    //글상세보기
    public Post getDetailPost(long id) {
        return postRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 글이 존재하지 않습니다."));
    }

    public List<Post> getPosts() {
        return postRepository.findAllByOrderByCreateAtDesc();
    }

    //비밀번호 확인
    public Boolean chkPassword(Long id, PasswordDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NullPointerException("아이디가 존재하지 않습니다."));
        return post.getPassword().equals(requestDto.getPassword());
    }
}
