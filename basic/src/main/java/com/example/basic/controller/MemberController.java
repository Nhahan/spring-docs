package com.example.basic.controller;

import com.example.basic.entity.Member;
import com.example.basic.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public List<Member> getMembers() {
        return memberService.getMembers();
    }

    @GetMapping("/members/{id}")
    public Member getMember(@PathVariable("id") Long id) {
        return memberService.getMember(id);
    }

    @PostMapping("/members")
    public Long insertMember(@RequestBody Member member) {
        return memberService.insertMember(member);
    }
}
