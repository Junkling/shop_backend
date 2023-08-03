package com.example.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private String result;
    private ErrorCode errorCode;
    private String message;

}
