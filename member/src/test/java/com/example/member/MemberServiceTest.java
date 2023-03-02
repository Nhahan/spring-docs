package com.example.member;

import com.example.member.dto.MemberRequestDto;
import com.example.member.dto.MemberResponseDto;
import com.example.member.entity.Member;
import com.example.member.repository.MemberRepository;
import com.example.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void postMember() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("asd@asd.com", "asd");
        Member member = new Member(memberRequestDto.getEmail(), memberRequestDto.getPassword());
        memberService.postMember(memberRequestDto);
        assertThat(member.getEmail()).isEqualTo("asd@asd.com");

        System.out.println("================");
        System.out.println(member.getEmail());
        System.out.println("================");
    }

    @Test
    void getMembers() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("asd@asd.com", "asd");
        memberService.postMember(memberRequestDto);

        List<MemberResponseDto> members = memberService.getMembers();

        assertThat(members.size()).isEqualTo(1);

        System.out.println("================");
        for (MemberResponseDto memberResponseDto : members) {
            System.out.println(memberResponseDto.getEmail());
        }
        System.out.println("================");
    }

    @Test
    void getMember() {
        MemberRequestDto memberRequestDto = new MemberRequestDto("asd@asd.com", "asd");
        memberService.postMember(memberRequestDto);

        MemberResponseDto member = memberService.getMember(1L);

        System.out.println("================");
        System.out.println(member.getId());
        System.out.println(member.getEmail());
        System.out.println("================");
    }

    @Test
    void getMemberFromRepository() {
        memberRepository.save(new Member("asd@asd.com", "asd"));
        Member member = memberRepository.findById(1L).orElseThrow(() -> new IllegalArgumentException());

        System.out.println("================");
        System.out.println(member.getId());
        System.out.println(member.getEmail());
        System.out.println("================");
    }
}
