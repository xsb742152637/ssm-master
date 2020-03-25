<%@ page import="util.context.Context" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/1
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="master" uri="util.masterPage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

%>
<html>
<head>
    <title>
        <master:ContentPlaceHolder id="title"/>
    </title>

    <link rel="stylesheet" href="/public/layui/dist/css/layui.css"/>
    <link rel="stylesheet" href="/public/colors/default.css"/>
    <link rel="stylesheet" href="/theme/pc/main/res/index.css"/>

    <script type="text/javascript" src="/public/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/public/layui/src/layui.js"></script>
    <script type="text/javascript" src="/public/util/eiis.foundation.js"></script>
    <script type="text/javascript" src="/theme/pc/main/res/index.js"></script>

    <master:ContentPlaceHolder id="head"/>

</head>
<body class="layui-layout-body">
    <!-- 让IE8/9支持媒体查询，从而兼容栅格。需要放在body标签内 -->
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>

    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <div class="layui-logo">
                <img class="pc_logo" src="/theme/pc/main/res/pcLogo3.png"/>
            </div>
            <!-- 头部区域（可配合layui已有的水平导航） -->
            <%--<ul class="layui-nav layui-layout-left">--%>
                <%--<li class="layui-nav-item"><a href="">控制台</a></li>--%>
                <%--<li class="layui-nav-item"><a href="">商品管理</a></li>--%>
                <%--<li class="layui-nav-item"><a href="">用户</a></li>--%>
                <%--<li class="layui-nav-item">--%>
                    <%--<a href="javascript:;">其它系统</a>--%>
                    <%--<dl class="layui-nav-child">--%>
                        <%--<dd><a href="">邮件管理</a></dd>--%>
                        <%--<dd><a href="">消息管理</a></dd>--%>
                        <%--<dd><a href="">授权管理</a></dd>--%>
                    <%--</dl>--%>
                <%--</li>--%>
            <%--</ul>--%>
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <%--<img src="http://t.cn/RCzsdCq" class="layui-nav-img">--%>
                        <i class="layui-icon layui-icon-username"></i>
                        <shiro:user>
                            <shiro:principal property="memberName"/>
                        </shiro:user>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="">基本资料</a></dd>
                        <dd><a href="">安全设置</a></dd>
                    </dl>
                </li>
                <li class="layui-nav-item"><a href="" onclick="loginout()">退出</a></li>
            </ul>
        </div>

        <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                <ul class="layui-nav layui-nav-tree"  lay-shrink="all" lay-filter="my-menu">
                    <%--<li class="layui-nav-item layui-nav-itemed">--%>
                        <%--<a class="" href="javascript:;">所有商品</a>--%>
                        <dl class="layui-nav-child">
                            <dd><a href="javascript:;">列表一</a></dd>
                            <dd><a href="javascript:;">列表二</a></dd>
                            <dd><a href="javascript:;">列表三</a></dd>
                            <dd><a href="">超链接</a></dd>
                        </dl>
                    <%--</li>--%>
                    <%--<li class="layui-nav-item"><a href="">云市场</a></li>--%>
                    <%--<li class="layui-nav-item"><a href="">发布商品</a></li>--%>
                </ul>
            </div>
        </div>

        <div class="layui-body">
            <!-- 内容主体区域 -->
            <div style="padding: 15px;">
                <master:ContentPlaceHolder id="body"/>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        var thisMenuId = "<%=Context.getCurrent().getMenuTree() == null ? "" : Context.getCurrent().getMenuTree().getMenuId()%>";
        //JavaScript代码区域
        layui.use(['element'], function(){
            var $ = layui.jquery;
            var element = layui.element;

            $.ajax({
                url: "/core/menuTree/getMenuTree.do",
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    if(data != null && data.length > 0){
                        var parentE = $('ul[lay-filter="my-menu"]');
                        var a = 0;
                        _loadMenuTree(parentE,data,a);

                    }
                    console.log(data);

                    element.render('nav');
                },
                error: function (jqXHR) {
                    console.log(jqXHR);
                }
            });

            element.on('nav(my-menu)',function(){
               console.log('点击了')
            });

        });

        function _loadMenuTree(parentE,data,a){
            for(var i = 0 ; i < data.length ; i++){
                var d = data[i];
                var li = $('<li></li>').addClass('layui-nav-item' + (a == 0 ? ' layui-nav-itemed' : '') + (d.menuId == thisMenuId ? ' layui-this' : ''));
                li.append($('<a/>').attr('href',String.isNullOrWhiteSpace(d.url) ? 'javascript:;' : d.url).text(d.title));
                a++;
                if(d.children != null && d.children.length > 0){
                    var dl = $("<dl/>").addClass("layui-nav-child");
                    _loadMenuTree(dl,d.children);
                    dl.appendTo(li);
                }
                li.appendTo(parentE);
            }
        }

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
