<%@ page import="util.context.Context" %>
<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %><%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2020/4/7
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="maste" uri="util.masterpage"%>
<%
    String menuCode = request.getParameter("menuCode");
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree(menuCode);
    String title = menuTree.getTitle();
%>

<maste:ContentPage>
    <maste:Content contentPlaceHolderId="title"><%=title%></maste:Content>
    <maste:Content contentPlaceHolderId="head">

    </maste:Content>
    <maste:Content contentPlaceHolderId="body">
        <div class="layui-fluid">
            <div class="layui-row layui-col-space10">
                <div class="ayui-col-xs12 layui-col-sm6 layui-col-md4">
                    <div class="layui-card">
                        <table id="dicinfoTable" lay-filter="main"></table>
                    </div>
                </div>
                <div class="layui-col-xs12 layui-col-sm6 layui-col-md8">
                    <div class="layui-card">
                        <table id="dicdetailTable" lay-filter="det"></table>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="res/index.js"></script>
    </maste:Content>
</maste:ContentPage>
