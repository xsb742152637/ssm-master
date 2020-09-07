<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="master" uri="util.masterpage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree();
    String menuCode = Context.getMenuCode();
    String title = menuTree.getTitle();
%>

<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">
        <link rel="stylesheet" href="/model/app/markdate/res/index.css"/>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <div id="aaa">
            <button>测试</button>
        </div>
        <div class="layui-row layui-col-space10">
            <div class="layui-col-xs12 layui-col-sm12 layui-col-md4">
                <div id="test1"></div>
            </div>
        </div>


        <script type="text/javascript">

        </script>
        <script type="text/javascript" src="/model/app/markdate/res/index.js"></script>
    </master:Content>
</master:ContentPage>
