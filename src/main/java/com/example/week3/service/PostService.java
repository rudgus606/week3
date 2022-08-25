package com.example.week3.Service;

import com.example.week3.Dto.RequestDto.PostRequestDto;
import com.example.week3.Entity.Post;
import com.example.week3.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Post edit(Long id, PostRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        post.updateByRequestDto(requestDto);
        return postRepository.findById(id).get();
    }
}
