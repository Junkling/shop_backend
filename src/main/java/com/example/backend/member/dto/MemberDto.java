package com.example.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    private Long id;

    private String email;

    private String role;

    private String auth;

    public void changeAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }
}
