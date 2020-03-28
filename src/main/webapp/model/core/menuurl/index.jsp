<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %>
<%@ taglib prefix="master" uri="util.masterpage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
 Comment：
 Created by IntelliJ IDEA.
 User: xiucai
 Date: 2020/3/27 14:04
--%>
<%
    String menuCode = request.getParameter("menuCode");
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree(menuCode);
    String title = menuTree.getTitle();
%>

<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">

        <link rel="stylesheet" href="/model/core/menuurl/res/index.css"/>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <div class="layui-card">
<%--            <div class="layui-card-header" id="toolbarDemo" style="display: none;">--%>
<%--                <div class="layui-btn-container">--%>
<%--                    <button class="layui-btn layui-btn-sm" lay-event="add">--%>
<%--                        <i class="layui-icon layui-icon-add-1"></i>添加--%>
<%--                    </button>--%>
<%--                    <button class="layui-btn layui-btn-sm" lay-event="update">--%>
<%--                        <i class="layui-icon layui-icon-edit"></i>修改--%>
<%--                    </button>--%>
<%--                    <button class="layui-btn layui-btn-sm" lay-event="delete">--%>
<%--                        <i class="layui-icon layui-icon-delete"></i>删除--%>
<%--                    </button>--%>
<%--                </div>--%>
<%--            </div>--%>
            <div class="layui-card-body">
                <table id="mainTable" lay-filter="test"></table>
            </div>
        </div>

        <script type="text/html">
        </script>
        <script type="text/javascript" src="/model/core/menuurl/res/index.js"></script>
    </master:Content>
</master:ContentPage>
