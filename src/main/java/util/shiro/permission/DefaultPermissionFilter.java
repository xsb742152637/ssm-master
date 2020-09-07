package util.shiro.permission;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import util.context.Context;

import javax.servlet.*;
import java.io.IOException;

public class DefaultPermissionFilter implements Filter {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        try{
            //得到当前请求的路径
            ShiroHttpServletRequest shiroHttpServletRequest = (ShiroHttpServletRequest) request;
            String url = shiroHttpServletRequest.getRequestURI();

            System.out.println("验证请求:" + url);
            if(url.endsWith(".jsp")){
                Context.setMenuTree(request.getParameter("menuCode"));
            }

            //判断当前请求是否有权限,待完善
            boolean hasPermission = true;

            if (hasPermission) {
                request.getRequestDispatcher(url).forward(request, response);
            } else {
                request.getRequestDispatcher("/theme/pc/error/401.jsp").forward(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

}
