package hello.mvc.springmvc.old;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 핸들러 매핑에서 이 컨트롤러의 이름(스프링 빈의 이름)을 찾을 수 있어야한다.
 * RequestMapping이 우선인데 현재는 RequestMapping이 없으므로 스프링 빈의 이름으로 찾아진 것이다.
 */
@Component("/springmvc/old-controller")
public class OldController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");
        return null;
    }
}
