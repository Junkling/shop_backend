package com.example.backend.member.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
public class MemberRequest {

    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
    @NotNull
    private String role;
}
