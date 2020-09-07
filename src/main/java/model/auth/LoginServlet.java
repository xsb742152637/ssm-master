package model.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.core.guide.service.core.MenuEx;
import model.core.memberinfo.entity.CoreMemberInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import util.context.Context;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;


public class LoginServlet extends HttpServlet {
    static class LoginMessage {
        public String msg;
        public boolean error;
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException            if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException            if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        LoginMessage lm = new LoginMessage();

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String savePas = request.getParameter("savePas");
        lm.error = true;
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            lm.msg = "用户名或密码不能为空.";
        } else {
            if (SecurityUtils.getSubject().isAuthenticated() && ((CoreMemberInfoEntity) SecurityUtils.getSubject().getPrincipal()).getMemberName().equals(username)) {
                lm.msg = "当前会话已经是验证通过了的.";
                lm.error = false;
            } else {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                try {
                    SecurityUtils.getSubject().login(token);
                    if(StringUtils.isNotBlank(savePas) && Boolean.parseBoolean(savePas)){
                        token.setRememberMe(true);//记住我
                    }

                    //初始化当前登录人员的权限
                    Context.loadGuide();
                    lm.error = false;
                } catch (AuthenticationException e) {
                    if(e instanceof IncorrectCredentialsException){
                        lm.msg = "账号或密码不正确";
                    } else if(e instanceof UnknownAccountException){
                        lm.msg = "帐号不存在";
                    } else if (e instanceof LockedAccountException) {
                        lm.msg = "账号未启用";
                    } else {
                        lm.msg = "未知错误";
                    }
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    lm.msg = e.getMessage();
                }
            }
        }

        response.getWriter().print(OBJECT_MAPPER.writeValueAsString(lm));
        if (!lm.error) {
            if ("true".equalsIgnoreCase(request.getParameter("autoRedirect"))) {
                String successUrl = request.getParameter("successUrl");
                if (StringUtils.isBlank(successUrl) || successUrl.trim().length() == 0) {
                    successUrl = Context.SUCCESS_URL;
                }
                response.sendRedirect(successUrl);
            }

        }
    }
}
