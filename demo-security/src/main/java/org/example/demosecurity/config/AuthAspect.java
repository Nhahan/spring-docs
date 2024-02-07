package org.example.demosecurity.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.demosecurity.dto.UserAuth;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * API 호출 시, UserAuth 파라미터에 @Auth 어노테이션이 있는지 확인하는 Aspect
 * 추천하지 않는 이유: API를 호출하지 않으면 에러를 알 수 없음
 *
 * 비활성화 하려면 @Component를 주석처리 하세요
 */
@Aspect
//@Component
public class AuthAspect {

    @Before("execution(* org.example.demosecurity..*.*(..)) && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void checkAuthAnnotation(JoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();

        for (Parameter parameter : parameters) {
            if (parameter.getType().equals(UserAuth.class) && parameter.getAnnotation(Auth.class) == null) {
                throw new IllegalArgumentException("Missing @Auth annotation for UserAuth parameter");
            }
        }
    }
}
