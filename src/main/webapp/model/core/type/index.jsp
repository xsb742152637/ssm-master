<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/1
  Time: 13:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="master" uri="util.masterPage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String menuCode = request.getParameter("menuCode");
//    if(true){
//        TypeSelectEntity pse = AppTypeDetailService.getInstance().getTypeSelect(menuCode,"");
//        List<AppTypeDetailEntity> doingList = pse.getDoingList();
//        request.getRequestDispatcher(doingList.get(0).getDetailValue()).forward(request,response);
//        return;
//    }
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree(menuCode);
    String title = menuTree.getTitle();
%>
<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">
        <style>
            h4{
                color: blue;
            }
        </style>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <h4>这里是type</h4>
        <!-- 通过shiro自定义标签来控制需要权限的按钮-->
        <%--<shiro:hasRole name="admin">--%>
            <%--用户[<shiro:principal property="memberName"/>]拥有角色admin<br/><br/>--%>
        <%--</shiro:hasRole>--%>
        <%--<shiro:hasAnyRoles name="admin,user,member,经理">--%>
            <%--用户[<shiro:principal property="memberName"/>]拥有角色admin或user或member或经理<br/><br/>--%>
        <%--</shiro:hasAnyRoles>--%>
        <%--<shiro:lacksRole name="admin">--%>
            <%--用户[<shiro:principal property="memberName"/>]不拥有admin角色<br/><br/>--%>
        <%--</shiro:lacksRole>--%>
        <%--<shiro:hasPermission name="user:add">--%>
            <%--用户[<shiro:principal property="memberName"/>]拥有user:add权限<br/><br/>--%>
        <%--</shiro:hasPermission>--%>
        <%--<shiro:lacksPermission name="user:add">--%>
            <%--用户[<shiro:principal property="memberName"/>]不拥有user:add权限<br/><br/>--%>
        <%--</shiro:lacksPermission>--%>
    </master:Content>
</master:ContentPage>
