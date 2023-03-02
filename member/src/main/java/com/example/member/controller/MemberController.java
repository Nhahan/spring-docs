package com.example.member.controller;

import com.example.member.dto.MemberRequestDto;
import com.example.member.dto.MemberResponseDto;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public MemberResponseDto postMember(
            @RequestBody MemberRequestDto memberRequestDto
    ) {
        return memberService.postMember(memberRequestDto);
    }

    @GetMapping("/members")
    public List<MemberResponseDto> getMember() {
        return memberService.getMembers();
    }
}
