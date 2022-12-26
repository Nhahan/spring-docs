package com.example.basic.service;

import com.example.basic.entity.Member;
import com.example.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getMembers() {
        return memberRepository.findAll();
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("member not exist"));
    }

    public Long insertMember(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

}
