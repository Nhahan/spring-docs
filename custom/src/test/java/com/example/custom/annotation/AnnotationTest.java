package com.example.custom.annotation;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AnnotationTest {

    @Test
    void controllerAnnotationTest() {
        Reflections reflections = new Reflections("com.example.custom");

        List<Class<?>> classList = new ArrayList<>(reflections.getTypesAnnotatedWith(Controller.class));

        for (Class<?> c : classList) {
            log.debug("{}를 컨트롤러로 만드는 코드", c);
        }
    }

    @Test
    void serviceAnnotationTest() {
        Reflections reflections = new Reflections("com.example.custom");

        List<Class<?>> classList = new ArrayList<>(reflections.getTypesAnnotatedWith(Service.class));

        for (Class<?> c : classList) {
            log.debug("{}를 서비스로 만드는 코드", c);
        }
    }
}
