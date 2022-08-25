package com.example.week3.Entity;

import com.example.week3.Dto.RequestDto.PostRequestDto;
import lombok.*;

import javax.persistence.*;


@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Entity
public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private  Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    public Post(PostRequestDto requestDto,String nickName) {
        this.title = requestDto.getTitle();
        this.name = nickName;
        //this.password = requestDto.getPassword();
        this.content = requestDto.getContent();
    }

    //수정하기
    public void updateByRequestDto(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}