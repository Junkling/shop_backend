package com.example.backend.config.auth;

import com.example.backend.member.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Member member;

    private Long id;
    private String email;

    public PrincipalDetails(Member member) {
        this.member = member;
    }

    public PrincipalDetails(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> { return member.getRole();});

        return collector;
    }


    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
