package com.example.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequest {

    @Email(message = "email 형식이 잘못되었습니다.")
    @NotEmpty(message = "email 은 필수값 입니다.")
    private String email;
    @NotEmpty(message = "비밀번호는 필수값 입니다.")
    private String password;
    @NotEmpty(message = "비밀번호 확인은 필수값 입니다.")
    private String confirmPassword;
    private String role;
}
