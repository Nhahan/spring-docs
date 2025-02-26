package org.example.expert.config.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AdminAccessLoggingAspect {

    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    @Around("@annotation(org.example.expert.config.aop.annotation.Admin)")
    public Object logAdminApiAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        Long userId = (Long) request.getAttribute("userId");
        String url = request.getRequestURI();
        long requestTimestamp = System.currentTimeMillis();

        String requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());
        log.info("AOP - Admin API Request: userId={}, Timestamp={}, URL={}, RequestBody={}",
                userId, requestTimestamp, url, requestBody);

        Object result = joinPoint.proceed();

        String responseBody = objectMapper.writeValueAsString(result);
        log.info("AOP - Admin API Response: userId={}, Timestamp={}, URL={}, ResponseBody={}",
                userId, System.currentTimeMillis(), url, responseBody);

        return result;
    }
}
