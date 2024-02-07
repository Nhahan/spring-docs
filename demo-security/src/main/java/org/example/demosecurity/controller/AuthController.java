package org.example.demosecurity.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.demosecurity.config.Auth;
import org.example.demosecurity.dto.AuthResponse;
import org.example.demosecurity.dto.UserAuth;
import org.example.demosecurity.entity.Member;
import org.example.demosecurity.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j(topic = "AuthController")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 회원 가입
     */
    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody Member member, HttpServletResponse res) {
        AuthResponse dto = authService.signup(member);
        res.addHeader("Authorization", dto.getToken());
        return dto;
    }

    /**
     * 로그인
     */
    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody Member member, HttpServletResponse res) {
        AuthResponse dto = authService.signin(member);
        res.addHeader("Authorization", dto.getToken());
        return dto;
    }

    /**
     * ArgumentResolver를 사용해서 어떻게 JWT 토큰에서 userId를 꺼내오는지에 대한 예제
     */
    @GetMapping("/members")
    public List<Member> getMembers(UserAuth userAuth) {
        String userId = userAuth.getUserId();
        log.info("userAuth: {}", userId);
        return authService.getMembers();
    }
}
