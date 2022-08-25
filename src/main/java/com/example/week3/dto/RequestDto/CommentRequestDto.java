package com.example.week3.Dto.RequestDto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String content;
}