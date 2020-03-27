package util;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Comment：
 * Created by IntelliJ IDEA.
 * User: xiucai
 * Date: 2020/3/27 14:46
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver{

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        exception.printStackTrace();

        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        mv.setView(view);
        mv.addObject("error", "1");
        mv.addObject("msg", "系统错误：" + exception.getMessage());
        return null;
    }
}
