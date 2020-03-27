package util.shiro.permission;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.util.WebUtils;
import util.context.Context;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
            //判断当前请求是否有权限,待完善
            boolean hasPermission = true;

            Context.createContext((HttpServletRequest) request,(HttpServletResponse) response);
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
