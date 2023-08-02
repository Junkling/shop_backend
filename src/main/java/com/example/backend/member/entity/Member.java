package com.example.backend.member.entity;

import com.example.backend.member.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(length = 50, nullable = false)
    private String role;

    private LocalDateTime createDate;

    @PrePersist
    public void createDate(){
        this.createDate = LocalDateTime.now();
    }

    public Member(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public MemberDto toDto() {
        MemberDto memberDto = new MemberDto(this.id, this.email, this.role);
        return memberDto;
    }
}
