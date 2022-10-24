package hello.mvc.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Controller는 view resolver를 return하지만
 * @RestController는 스트링 그대로 반환해준다.
 */
@Slf4j
@RestController
public class LogTestController {

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);
        log.info("info log={}", name);

        return "ok";
    }
}
