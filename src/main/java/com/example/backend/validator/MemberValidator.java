package com.example.backend.validator;

import com.example.backend.exception.CustomException;
import com.example.backend.exception.ErrorCode;
import com.example.backend.member.dto.MemberRequest;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberValidator{
    private final MemberRepository memberRepository;

    public void validate(Object target) {
        MemberRequest member = (MemberRequest) target;
        if (memberRepository.findByEmail(member.getEmail()) != null) {

            throw new CustomException(ErrorCode.DUPLICATED_USER_NAME);
        }
    }
}
