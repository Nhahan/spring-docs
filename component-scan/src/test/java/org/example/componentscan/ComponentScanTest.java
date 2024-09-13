package org.example.componentscan;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class ComponentScanTest {

    @Test
    void testCustomComponentScan() throws Exception {
        SimpleIoCContainer container = new SimpleIoCContainer();

        // 스캔할 패키지 설정
        String packageName = "org.example.componentscan";
        Reflections reflections = new Reflections(packageName); // 코드 조작 같은 것.

        // @CustomController 이 붙은 놈들 싹 다 긁어와
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(CustomService.class);

        // 스캔된 클래스를 Bean으로 등록
        for (Class<?> clazz : annotatedClasses) {
            container.registerBean(clazz);
            System.out.println("Scanned and registered: " + clazz.getName());
        }

        // 등록된 빈 출력
        container.printBeans();
    }
}
