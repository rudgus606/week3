package com.example.week3.util;

import lombok.Getter;

@Getter
public class Response <T> {
    boolean checkSuccess;
    T data;
    ErrorList errorList;

    public Response(boolean checkSuccess, T data,ErrorList errorList) {
        this.checkSuccess = checkSuccess;
        this.data = data;
        this.errorList =errorList;
    }


}