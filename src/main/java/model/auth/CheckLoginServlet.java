
package model.auth;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CheckLoginServlet extends HttpServlet {

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
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        LoginMessage lm = new LoginMessage();
        lm.error = true;
        if (SecurityUtils.getSubject().isAuthenticated()) {
            lm.mes = "当前会话已经是验证通过了的.";
            lm.error = false;
        } else {
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                lm.mes = "用户名或密码不能为空.";
            } else {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                try {
                    SecurityUtils.getSubject().login(token);
                    lm.error = false;
                } catch (AuthenticationException e) {
                    if (e instanceof LockedAccountException) {
                        lm.mes = "用户密码不正确.";
                    } else {
                        lm.mes = e.getMessage();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    lm.mes = e.getMessage();
                }
            }
        }

        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        out.println(JSONObject.fromObject(lm));//返回jsonp格式数据
        out.flush();
        out.close();
    }
}
