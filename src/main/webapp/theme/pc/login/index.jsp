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
    <script rel="stylesheet" src="/public/jquery/easyui-1.7.0/jquery.min.js"></script>
</head>
<body>
<%--<form id="login_form" action="/anon/loginservlet" method="post" onsubmit="return check()">--%>
用户名：<input  type="text" name="username" value="xc"/><br><br>
密&nbsp;&nbsp;&nbsp;&nbsp;码：<input  type="password" name="password" value="111"/><br><br><br>
<button onclick="login()" >登录</button>

    <script type="text/javascript">
        var successUrl = "<%=successUrl%>";
        $(function(){

        });
        function login(){
            var username = $("input[name='username']").val();
            var password = $("input[name='password']").val();

            var flg = false;
            if(username == ""){
                alert("请输入用户名");
                flg = true;
            }else if(password == ""){
                alert("请输入密码");
                flg = true;
            }
            if(flg) {
                return;
            }
            $.ajax({
                url: "/anon/loginservlet",
                dataType: 'json',
                type: "POST",
                data: {username: username,password: password},
                error: function (jqXHR) {
                    console.log(jqXHR);
                },
                success: function (data) {
                    console.log(data);
                    if (data.error) {
                        alert(data.msg);
                    } else {
                        window.location.href = successUrl;
                    }
                }
            });
        }
    </script>
</body>
</html>
