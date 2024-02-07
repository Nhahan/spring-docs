package org.example.demosecurity.config;

import lombok.extern.slf4j.Slf4j;
import org.example.demosecurity.dto.UserAuth;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;

/**
 * 스프링 실행 시점에 @RestController가 붙은 클래스의 메소드에 UserAuth 파라미터에 @Auth 어노테이션이 있는지 확인하는 리스너
 * AuthAspect와 다르게 API를 호출하지 않아도 에러를 알 수 있음
 *
 * 비활성화 하려면 @Component를 주석처리 하세요
 */
@Slf4j(topic = "AuthAnnotationVerifierListener")
//@Component
public class AuthAnnotationVerifierListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) event.getApplicationContext();
        String[] beanNames = applicationContext.getBeanDefinitionNames();

        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = applicationContext.getBeanFactory().getBeanDefinition(beanName);
            if (beanDefinition.getBeanClassName() != null) {
                try {
                    Class<?> beanClass = Class.forName(beanDefinition.getBeanClassName());
                    if (beanClass.isAnnotationPresent(RestController.class)) {
                        Method[] methods = beanClass.getMethods();
                        for (Method method : methods) {
                            for (MethodParameter parameter : new HandlerMethod(beanClass, method).getMethodParameters()) {
                                if (parameter.getParameterType().equals(UserAuth.class) && !parameter.hasParameterAnnotation(Auth.class)) {
                                    throw new IllegalStateException("Missing @Auth annotation on UserAuth parameter in " + method.getName() + " of " + beanClass.getName());
                                }
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    log.debug("Something went wrong", e);
                    throw new IllegalStateException("Something went wrong", e);
                }
            }
        }
    }
}
