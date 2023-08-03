package com.example.backend.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    private String result;
    private ErrorCode errorCode;
    private String message;

    public CustomException(ErrorCode errorCode) {
        this.result = "ERROR";
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    public ExceptionDto toDto() {
        return new ExceptionDto(this.result, this.errorCode, this.message);
    }
}
