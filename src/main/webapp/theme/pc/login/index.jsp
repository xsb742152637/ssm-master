<%@ page import="org.apache.shiro.SecurityUtils" %><%--
  Created by IntelliJ IDEA.
  User: xiucai
  Date: 2020/1/13
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (SecurityUtils.getSubject().isAuthenticated()) {
        response.sendRedirect("/theme/pc/index.jsp");
    }

    String successUrl = "/theme/pc/index.jsp";
%>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="/public/colors/default.css"/>
    <link rel="stylesheet" href="/public/jquery/easyui-1.7.0/themes/default/easyui.css"/>
    <link rel="stylesheet" href="/public/jquery/easyui-1.7.0/themes/icon.css"/>
    <link rel="stylesheet" href="/theme/pc/login/res/index.css"/>

    <script type="text/javascript" src="/public/jquery/easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="/public/jquery/easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/public/util/eiis.foundation.js"></script>
    <script type="text/javascript" src="/theme/pc/login/res/index.js"></script>
</head>
<body>
    <div class="qt">

    </div>
    <div class="zfx">
        <div class="zfx-w">
            <div class="zfx-n zfx-n1"></div>
            <div class="zfx-n zfx-n2"></div>

            <div class="zfx-n3">
                <div>
                    <div class="n3-left">用户名：</div>
                    <div class="n3-right"><input  type="text" name="username" placeholder="请输入用户名"/></div>
                </div>
                <div>
                    <div class="n3-left">密码：</div>
                    <div class="n3-right"><input  type="password" name="password" placeholder="请输入密码"/></div>
                </div>
                <div>
                    <div class="n3-left"></div>
                    <div class="n3-right">
                        <input  type="checkbox" name="savePas" id="savePas" />
                        <label for="savePas" style="cursor: pointer;">记住密码</label>
                    </div>
                </div>
                <div>
                    <div class="n3-left"></div>
                    <div class="n3-right">
                        <div class="but" onclick="login()" >登录</div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var successUrl = "<%=successUrl%>";
    </script>
</body>
</html>
