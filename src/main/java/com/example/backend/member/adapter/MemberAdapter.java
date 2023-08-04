package com.example.backend.member.adapter;

import com.example.backend.member.auth.PrincipalDetails;
import com.example.backend.member.entity.Member;
import lombok.Getter;

@Getter
public class MemberAdapter extends PrincipalDetails {
    private Member member;

    public MemberAdapter(Member member) {
        super(member);
    }

    public MemberAdapter(Long id, String email, String role, Member member) {
        super(id, email, role);
        this.member = member;
    }
}
