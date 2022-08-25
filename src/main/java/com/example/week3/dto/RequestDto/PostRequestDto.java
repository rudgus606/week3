package com.example.week3.Dto.RequestDto;

import com.example.week3.util.Security;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class PostRequestDto {
    //제목 작성자명 비밀번호 작성내용
    private String title;
    private String content;
}