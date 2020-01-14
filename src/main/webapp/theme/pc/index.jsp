<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/1
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>这里是首页</title>
    <script rel="stylesheet" src="/public/jquery/easyui-1.7.0/jquery.min.js"></script>
</head>
<body>
这里是首页
<button onclick="loginout()">退出</button>
<script type="text/javascript">
    function loginout(){
        $.ajax({
            url: "/anon/logoutServlet",
            dataType: 'text',
            type: "POST",
            success: function (data) {
                window.location.reload();
            },
            error: function (jqXHR) {
                console.log(jqXHR);
            }
        });
    }
</script>
</body>
</html>
