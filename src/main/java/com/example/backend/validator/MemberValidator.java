package com.example.backend.validator;

import com.example.backend.member.dto.MemberRequest;
import com.example.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Service
public class MemberValidator implements Validator {
    private final MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberRequest member = (MemberRequest) target;
        if (memberRepository.findByEmail(member.getEmail())!=null) {
            errors.rejectValue("email","email","ID 중복입니다.");
        }
        if (!member.getPassword().equals(member.getConfirmPassword())) {
            errors.rejectValue("password","password","비밀번호가 다릅니다.");
        }
    }
}
