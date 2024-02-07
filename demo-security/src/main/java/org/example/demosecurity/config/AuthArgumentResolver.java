package org.example.demosecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.example.demosecurity.dto.UserAuth;
import org.example.demosecurity.utils.JwtUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j(topic = "AuthArgumentResolver")
@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserAuth.class) && parameter.hasParameterAnnotation(Auth.class);
    }

    @Override
    public UserAuth resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String token = webRequest.getHeader("Authorization");

        if (JwtUtils.validateToken(token)) {
            String userId = JwtUtils.getUserIdFromToken(token);
            return new UserAuth(userId);
        }
        log.error("Token is not valid.");
        throw new IllegalArgumentException("Token is not valid.");
    }
}
