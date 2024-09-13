package org.example.componentscan;

import lombok.RequiredArgsConstructor;

@CustomController
@RequiredArgsConstructor
public class ByeController {

    public void sayHello() {
        System.out.println("Hello from CustomController!");
    }
}
