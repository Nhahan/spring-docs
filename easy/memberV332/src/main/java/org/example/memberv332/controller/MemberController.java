package org.example.memberv332.controller;

import lombok.RequiredArgsConstructor;
import org.example.memberv332.dto.MemberSaveRequestDto;
import org.example.memberv332.dto.MemberSaveResponseDto;
import org.example.memberv332.dto.MemberSimpleResponseDto;
import org.example.memberv332.service.MemberService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MemberSaveResponseDto> saveMember(@RequestBody MemberSaveRequestDto requestDto) {
        return ResponseEntity.ok(memberService.saveMember(requestDto));
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberSimpleResponseDto>> getMembers() {
        return ResponseEntity.ok(memberService.getMembers());
    }

}
