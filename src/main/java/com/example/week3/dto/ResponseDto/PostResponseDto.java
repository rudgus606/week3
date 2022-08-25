package com.example.week3.Dto.ResponseDto;

import com.example.week3.Entity.Comment;
import com.example.week3.Entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    //제목 작성자명 작성내용
    //비밀 번호는 제외
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;
    private String name;
    private String content;



    public PostResponseDto(Post post) {
        this.id= post.getId();
//        this.createdAt=post.getCreatedAt();
        this.modifiedAt=post.getModifiedAt();
        this.title=post.getTitle();
        this.name=post.getName();
        this.content=post.getContent();
    }


}