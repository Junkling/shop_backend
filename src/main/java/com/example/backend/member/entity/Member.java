package com.example.backend.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
