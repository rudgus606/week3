package com.example.week3.Service;

import com.example.week3.Dto.RequestDto.CommentRequestDto;
import com.example.week3.Entity.Comment;
import com.example.week3.Repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Comment edit(Long id, CommentRequestDto requestDto){
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        comment.updateByRequestDto(requestDto);
        return commentRepository.findById(id).get();
    }
}