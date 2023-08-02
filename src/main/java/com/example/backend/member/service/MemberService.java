package com.example.backend.member.service;

import com.example.backend.member.dto.LoginDto;
import com.example.backend.member.dto.MemberDto;
import com.example.backend.member.dto.MemberRequest;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

//    public MemberDto findIdByEmailAndPassword(LoginDto loginDto) {
//
//        Member member = memberRepository.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
//        if (member == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당하는 계정이 없습니다.");
//        MemberDto memberDto = member.toDto();
//        return memberDto;
//    }

    @Transactional
    public MemberDto signup(MemberRequest res) throws IOException {
        if(memberRepository.findByEmail(res.getEmail())!=null) throw new IOException("이미 존재하는 ID입니다.");
//        if(!res.getPassword().equals(res.getConfirmPassword())) throw new IOException("패스워드 확인이 일치하지 않습니다.");
        String rawPassword = res.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        Member member = new Member(res.getEmail(), encPassword, res.getRole()==null||res.getRole().equals("")?"user":res.getRole());

        Member userEntity = memberRepository.save(member);
        return userEntity.toDto();
    }
    public MemberDto login(LoginDto loginDto) throws IOException {

        Member member = memberRepository.findByEmail(loginDto.getEmail());

        if (!bCryptPasswordEncoder.matches(loginDto.getPassword(), member.getPassword()))
            throw new IOException("Passwords do not match");

        MemberDto authentication = member.toDto();

        return authentication;
    }

    public MemberDto findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberDto memberDto = member.toDto();
        return memberDto;
    }
}
