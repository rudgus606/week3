package com.example.week3.Dto;

import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class SignUpDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-z0-9]{4,12}$")
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,32}$")
    private String password;

    private String passwordConfirm;
}