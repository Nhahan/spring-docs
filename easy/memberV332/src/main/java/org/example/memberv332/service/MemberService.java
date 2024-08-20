package org.example.memberv332.service;

import lombok.RequiredArgsConstructor;
import org.example.memberv332.dto.MemberSaveRequestDto;
import org.example.memberv332.dto.MemberSaveResponseDto;
import org.example.memberv332.dto.MemberSimpleResponseDto;
import org.example.memberv332.entity.Member;
import org.example.memberv332.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberSaveResponseDto saveMember(MemberSaveRequestDto memberSaveRequestDto) {
        Member newMember = new Member(memberSaveRequestDto.getName());
        Member savedMember = memberRepository.save(newMember);

        return new MemberSaveResponseDto(savedMember.getName());
    }

    public List<MemberSimpleResponseDto> getMembers() {
        List<Member> members = memberRepository.findAll();

        List<MemberSimpleResponseDto> memberSimpleResponseDtos = new ArrayList<>();
        for (Member member : members) {
            memberSimpleResponseDtos.add(new MemberSimpleResponseDto(member.getId(), member.getName()));
        }

        return memberSimpleResponseDtos;
    }
}
