package com.example.member.service;

import com.example.member.dto.MemberRequestDto;
import com.example.member.dto.MemberResponseDto;
import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto postMember(MemberRequestDto memberRequestDto) {
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        memberRepository.save(member);

        return new MemberResponseDto(member.getId(), member.getEmail());

    }

    public List<MemberResponseDto> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(member -> new MemberResponseDto(member.getId(), member.getEmail())).toList();
    }

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new NullPointerException());
        return new MemberResponseDto(member.getId(), member.getEmail());
    }
}
