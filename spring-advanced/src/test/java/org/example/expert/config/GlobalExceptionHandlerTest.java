package org.example.expert.config;

import org.example.expert.domain.auth.exception.AuthException;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.common.exception.ServerException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void 유효하지_않은_요청_예외_처리_테스트() {
        // given
        String errorMessage = "유효하지 않은 요청입니다";
        InvalidRequestException exception = new InvalidRequestException(errorMessage);

        // when
        ResponseEntity<Map<String, Object>> response = handler.invalidRequestExceptionException(exception);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.name(), body.get("status"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.get("code"));
        assertEquals(errorMessage, body.get("message"));
    }

    @Test
    void 인증_예외_처리_테스트() {
        // given
        String errorMessage = "인증에 실패했습니다";
        AuthException exception = new AuthException(errorMessage);

        // when
        ResponseEntity<Map<String, Object>> response = handler.handleAuthException(exception);

        // then
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.UNAUTHORIZED.name(), body.get("status"));
        assertEquals(HttpStatus.UNAUTHORIZED.value(), body.get("code"));
        assertEquals(errorMessage, body.get("message"));
    }

    @Test
    void 서버_예외_처리_테스트() {
        // given
        String errorMessage = "서버 오류가 발생했습니다";
        ServerException exception = new ServerException(errorMessage);

        // when
        ResponseEntity<Map<String, Object>> response = handler.handleServerException(exception);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), body.get("status"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("code"));
        assertEquals(errorMessage, body.get("message"));
    }

    @Test
    void 일반_예외_처리_테스트() {
        // given
        Exception exception = new RuntimeException("일반 예외 발생");

        // when
        ResponseEntity<Map<String, Object>> response = handler.handleGenericException(exception);

        // then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), body.get("status"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("code"));
        assertEquals("Internal Server Error", body.get("message"));
    }

    @Test
    void 에러_응답_생성_테스트() {
        // given
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = "테스트 에러 메시지";

        // when
        ResponseEntity<Map<String, Object>> response = handler.getErrorResponse(status, message);

        // then
        assertEquals(status, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(status.name(), body.get("status"));
        assertEquals(status.value(), body.get("code"));
        assertEquals(message, body.get("message"));
    }
}
