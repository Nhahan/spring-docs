package org.example.concurrency;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam String test) {
        return test;
    }
}
