package com.example.week3.Dto.ResponseDto;

import com.example.week3.Entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String author;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.id= comment.getId();
//        this.createdAt=comment.getCreatedAt();
        this.modifiedAt=comment.getModifiedAt();
        this.author=comment.getAuthor();
        this.content=comment.getContent();
    }
}