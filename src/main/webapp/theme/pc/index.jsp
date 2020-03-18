<%@ page import="model.core.menuTree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/1
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="master" uri="util.masterPage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String menuCode = "index";
//    if(true){
//        TypeSelectEntity pse = AppTypeDetailService.getInstance().getTypeSelect(menuCode,"");
//        List<AppTypeDetailEntity> doingList = pse.getDoingList();
//        request.getRequestDispatcher(doingList.get(0).getDetailValue()).forward(request,response);
//        return;
//    }
//    CoreMenuTreeInfoEntity menuTree = Context.getCurrent().getMenuTree(menuCode);
//    String title = menuTree.getTitle();
    String title = "首页";
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
        <h4>这里是首页</h4>
    </master:Content>
</master:ContentPage>
