package util.filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import util.context.ApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ContextInitFilter implements Filter {
    private static boolean isAutoLogin = false;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        try{
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            //解决部分浏览器偶尔会丢失session的问题所加的代码 tj
            httpServletRequest.getSession();
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            //设置request与response的编码
            httpServletRequest.setCharacterEncoding("utf-8");
            httpServletResponse.setCharacterEncoding("utf-8");
            httpServletResponse.addHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
            //开启 Cookie 支持，但 Origin 不能为 *。
            httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.addHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");

            if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())){
                return;
            }
            String url = ((HttpServletRequest) request).getRequestURI();

            System.out.println("filter：" + url);
            try{
                //已登录
                if (SecurityUtils.getSubject().isAuthenticated()) {
                    //判断登录是否超时

                }else{
                    System.out.println("请登录");
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            chain.doFilter(httpServletRequest, httpServletResponse);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
