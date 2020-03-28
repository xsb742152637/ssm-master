<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/3/26
  Time: 21:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="master" uri="util.masterpage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String menuCode = request.getParameter("menuCode");
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree(menuCode);
    String title = menuTree.getTitle();
%>

<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">

    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <jsp:include page="detail.jsp"></jsp:include>

    </master:Content>
</master:ContentPage>
