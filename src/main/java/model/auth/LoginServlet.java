package model.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.core.memberInfo.service.CoreMemberInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LoginServlet extends HttpServlet {
    @Autowired
    private CoreMemberInfoService service;

    static class LoginMessage {
        public String mes;
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

    private final static ObjectMapper objectMapper = new ObjectMapper();
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
        lm.error = true;
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            lm.mes = "用户名或密码不能为空.";
        } else {
            if ( SecurityUtils.getSubject().isAuthenticated() && SecurityUtils.getSubject().getPrincipal().equals(username)) {
                lm.mes = "当前会话已经是验证通过了的.";
                lm.error = false;
            } else {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                try {
                    SecurityUtils.getSubject().login(token);
                    SecurityUtils.getSubject().checkPermission("user:add");
                    lm.error = false;
                } catch (AuthenticationException e) {
                    if(e instanceof IncorrectCredentialsException || e instanceof UnknownAccountException){
                        lm.mes = "账号或密码不正确.";
                    } else if (e instanceof LockedAccountException) {
                        lm.mes = "账号已被冻结.";
                    } else {
                        lm.mes = e.getMessage();
                        response.sendRedirect("/theme/pc/login/index.jsp");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    lm.mes = e.getMessage();
                }
            }
        }

        response.getWriter().print(objectMapper.writeValueAsString(lm));
        if (!lm.error) {
            if ("true".equalsIgnoreCase(request.getParameter("autoRedirect"))) {
                String successUrl = request.getParameter("successUrl");
                if (StringUtils.isBlank(successUrl) || successUrl.trim().length() == 0) {
                    successUrl = "/theme/pc/index.jsp";
                }
                response.sendRedirect(successUrl);
            }

        }
    }
}
