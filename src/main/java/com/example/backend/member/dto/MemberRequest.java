package com.example.backend.member.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

@Getter
public class MemberRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String confirmPassword;
    @NotNull
    private String role;
}
