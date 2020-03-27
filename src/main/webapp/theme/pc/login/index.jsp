<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="util.context.Context" %><%--
  Created by IntelliJ IDEA.
  User: xiucai
  Date: 2020/1/13
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (SecurityUtils.getSubject().isAuthenticated()) {
        response.sendRedirect(Context.SUCCESS_URL);
    }

%>
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="/public/layui/src/css/layui.css"/>
    <link rel="stylesheet" href="/public/colors/default.css"/>
    <link rel="stylesheet" href="/theme/pc/login/res/index.css"/>

    <script type="text/javascript" src="/public/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/public/layui/src/layui.js"></script>
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
                <from class="layui-form">
                    <div class="layui-form-item">
                        <label class="layui-form-label">用户名：</label>
                        <div class="layui-input-block">
                            <!-- lay-verify="required" 表示验证类型为必填 -->
                            <!-- autocomplete="off" 表示表单不使用缓存信息 -->
                            <input type="text" name="username" required  lay-verify="required" placeholder="请输入用户名" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">密码：</label>
                        <div class="layui-input-block">
                            <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">记住密码：</label>
                        <div class="layui-input-block">
                            <!-- lay-skin="primary" 表示用原始风格 -->
                            <input type="checkbox" name="savePas" value="true" lay-skin="primary">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button class="layui-btn" lay-submit lay-filter="formDemo">登录</button>
                        </div>
                    </div>
                </from>

            </div>
        </div>
    </div>

    <script type="text/javascript">
        var successUrl = "<%=Context.SUCCESS_URL%>";
    </script>
</body>
</html>
