package org.example.demosecurity.service;

import lombok.RequiredArgsConstructor;
import org.example.demosecurity.dto.AuthResponse;
import org.example.demosecurity.entity.Member;
import org.example.demosecurity.repository.MemberRepository;
import org.example.demosecurity.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public AuthResponse signup(Member input) {
        Member found = memberRepository.findByEmail(input.getEmail());
        if (found != null) {
            throw new IllegalArgumentException("이미 가입된 유저입니다.");
        }
        Member member = memberRepository.save(input);

        String token = JwtUtils.generateToken(member.getId());

        return new AuthResponse(member.getId(), member.getEmail(), token);
    }

    public AuthResponse signin(Member member) {
        Member found = memberRepository.findByEmail(member.getEmail());
        if (found == null) {
            throw new IllegalArgumentException("가입되지 않은 유저입니다.");
        }

        String token = JwtUtils.generateToken(found.getId());

        return new AuthResponse(found.getId(), found.getEmail(), token);
    }

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }
}
