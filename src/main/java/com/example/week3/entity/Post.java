package com.example.week3.entity;

import com.example.week3.dto.PostRequestDto;
import com.example.week3.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity                         // 엔티티(테이블임을 선언)
@NoArgsConstructor              // 기본생성자 생성생략
@Getter
public class Post extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;            // 게시물 고유번호

    @Column(nullable = false)   // not Null
    private String title;       // 게시물 이름

    @Column(nullable = false)
    private String author;      // 게시물 작성자이름

    @Column(nullable = false)
    private String content;     // 게시물 내용

    @Column(nullable = false)
    private String password;    // 비밀번호

    public Post(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.content = requestDto.getContent();
        this.password = requestDto.getPassword();
    }
}
