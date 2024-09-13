package org.example.componentscan;

import lombok.RequiredArgsConstructor;

@CustomController
@RequiredArgsConstructor
public class HelloController {

    public void sayHello() {
        System.out.println("Hello from CustomController!");
    }
}
