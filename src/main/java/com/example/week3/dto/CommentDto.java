package com.example.week3.Dto;


import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    @NotNull
    private Long postId;

    @NotNull
    private String content;

}