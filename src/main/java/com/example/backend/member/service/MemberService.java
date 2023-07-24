package com.example.backend.member.service;

import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long findIdByEmailAndPassword(String email, String password) {
        Member member = memberRepository.findByEmailAndPassword(email, password);
        if(member==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당하는 계정이 없습니다.");
        return member.getId();
    }
}
