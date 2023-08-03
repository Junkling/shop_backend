package com.example.backend.exception.service;

import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;

public class ExceptionService {

    public void login() {
        throw new CustomException(ErrorCode.USERNAME_NOT_FOUND);
    }

    public void writeComment() {
        throw new CustomException(ErrorCode.INVALID_PERMISSION);
    }

    public void join() {
        throw new CustomException(ErrorCode.DUPLICATED_USER_NAME);
    }

    public void editComment() {
        throw new CustomException(ErrorCode.DATABASE_ERROR);
    }}
