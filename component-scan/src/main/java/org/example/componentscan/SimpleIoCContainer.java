package org.example.componentscan;

import java.util.HashMap;
import java.util.Map;

public class SimpleIoCContainer {

    private Map<Class<?>, Object> beans = new HashMap<>(); // 이 자식이 뭔진 모르겠지만, IoC Container라매... 그럼 Bean이 들어가겠네.

    public void registerBean(Class<?> clazz) throws Exception {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        beans.put(clazz, instance);
    }

    public <T> T getBean(Class<T> clazz) {
        return clazz.cast(beans.get(clazz));
    }

    public void printBeans() {
        beans.keySet().forEach(clazz -> System.out.println("Registered Bean: " + clazz.getName()));
    }
}

