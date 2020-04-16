<%@ page import="util.context.Context" %>
<%@ page import="model.core.basesetting.CoreBaseSetting" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/1
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="master" uri="util.masterpage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

%>
<html>
<head>
    <title><master:ContentPlaceHolder id="title"/></title>

    <link rel="stylesheet" href="/public/layui/src/css/layui.css"/>
<%--    <link rel="stylesheet" href="/public/layui/src/css/radius.css"/>--%>
    <link rel="stylesheet" href="/public/layui/src/css/admin.css"/>
    <link rel="stylesheet" href="/public/colors/default.css"/>
    <link rel="stylesheet" href="/theme/pc/main/res/index.css"/>

    <script type="text/javascript" src="/public/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/public/layui/src/layui.js"></script>
    <script type="text/javascript" src="/public/util/eiis.foundation.js"></script>

    <master:ContentPlaceHolder id="head"/>
</head>
<body class="layui-layout-body <%=CoreBaseSetting.getBaseSetting()%>">
    <!-- 让IE8/9支持媒体查询，从而兼容栅格。需要放在body标签内 -->
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>

    <div class="layadmin-tabspage-none">
        <div class="layui-layout layui-layout-admin">
            <div class="layui-header">

                <!-- 头部区域（可配合layui已有的水平导航） -->
                <div class="layui-layout-left">
                    <div class="LAY_app_flexible" layadmin-event="flexible" title="侧边伸缩">
                        <i class="layui-icon layui-icon-shrink-right"></i>
                    </div>
                    <div class="layui-breadcrumb"><a>首页</a><a>国际新闻</a></div>
                </div>

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
                    <li class="layui-nav-item">
                        <a href="" onclick="loginout()" style="color: #FF5722">
                            <i class="layui-icon layui-icon-logout"></i>
                            退出
                        </a>
                    </li>
                </ul>
            </div>

            <div class="layui-side layui-side-menu">
                <div class="layui-side-scroll">
                    <div class="layui-logo">
                        <span class="logo-text" style="display: none">G&nbsp;H</span>
                        <cite>
                            <img class="pc_logo" src="/theme/pc/main/res/pcLogo3.png"/>
                        </cite>
                    </div>
                    <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                    <ul class="layui-nav layui-nav-tree"  lay-shrink="all" lay-filter="layadmin-system-side-menu" id="LAY-system-side-menu"></ul>
                </div>
            </div>

            <div class="layui-body">
                <!-- 内容主体区域 -->
                <div style="padding: 15px;">
                    <master:ContentPlaceHolder id="body"/>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        const isAdmini = <%=Context.getCurrent().isAdmini()%>;
        const thisMenuId = "<%=Context.getCurrent().getMenuTree() == null ? "" : Context.getCurrent().getMenuTree().getMenuId()%>";
    </script>
    <script type="text/javascript" src="/theme/pc/main/res/index.js"></script>
</body>
</html>
