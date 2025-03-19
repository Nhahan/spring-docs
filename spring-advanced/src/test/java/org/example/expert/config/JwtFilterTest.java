package org.example.expert.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.example.expert.domain.user.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtFilterTest {

    private JwtUtil jwtUtil;
    private JwtFilter jwtFilter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;

    private static final String SECRET_KEY = "4asdkkladasd13slkasdlkasdjhfjklasdfhlkdjsahflhksdf";

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();
        Field secretKeyField = JwtUtil.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtUtil, SECRET_KEY);

        jwtUtil.init();
        jwtFilter = new JwtFilter(jwtUtil);

        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    void auth_API는_JwtFilter에서_JWT를_검증하지_않는다() throws ServletException, IOException {
        // given - JWT 없음
        request.setRequestURI("/auth/login");

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertNotNull(filterChain.getRequest());
    }

    @Test
    void 일반_API는_JwtFilter에서_JWT가_없으면_BAD_REQEUST를_던진다() throws ServletException, IOException {
        // given
        request.setRequestURI("/users");

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
        assertEquals("JWT 토큰이 필요합니다.", response.getErrorMessage());
    }

    @Test
    void 일반_API는_JwtFilter에서_USER_권한_JWT가_존재하면_통과한다() throws ServletException, IOException {
        // given
        request.setRequestURI("/users");

        // Authorization 헤더에 USER 권한 JWT 추가
        Long userId = 1L;
        String email = "user@test.com";
        UserRole userRole = UserRole.USER;
        String token = jwtUtil.createToken(userId, email, userRole);
        request.addHeader("Authorization", token);

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals(userId, request.getAttribute("userId"));
        assertEquals(email, request.getAttribute("email"));
        assertEquals(UserRole.USER.name(), request.getAttribute("userRole"));
        assertNotNull(filterChain.getRequest());
    }

    @Test
    void 어드민_API는_JwtFilter에서_ADMIN_권한_JWT가_존재하면_통과한다() throws ServletException, IOException {
        // given
        request.setRequestURI("/admin/test");

        // Authorization 헤더에 ADMIN 권한 JWT 추가
        String token = jwtUtil.createToken(1L, "admin@test.com", UserRole.ADMIN);
        request.addHeader("Authorization", token);

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertNotNull(filterChain.getRequest());
    }

    @Test
    void 어드민_API는_JwtFilter에서_ADMIN_권한_JWT가_아니라면_FORBIDDEN을_던진다() throws ServletException, IOException {
        // given
        request.setRequestURI("/admin/dashboard");

        // Authorization 헤더에 USER 권한 JWT 추가
        String token = jwtUtil.createToken(1L, "user@test.com", UserRole.USER);
        request.addHeader("Authorization", token);

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
        assertEquals("관리자 권한이 없습니다.", response.getErrorMessage());
    }

    @Test
    void 유효하지_않은_JWT는_JwtFilter를_통과하지_못한다() throws ServletException, IOException {
        // given
        request.setRequestURI("/api/users");
        request.addHeader("Authorization", "Bearer invalid.token.format");

        // when
        jwtFilter.doFilter(request, response, filterChain);

        // then
        assertTrue(response.getStatus() >= 400);
    }
}
