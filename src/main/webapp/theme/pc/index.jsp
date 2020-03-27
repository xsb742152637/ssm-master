<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/1/1
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="master" uri="util.masterpage" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String menuCode = request.getParameter("menuCode");
//    if(true){
//        TypeSelectEntity pse = AppTypeDetailService.getInstance().getTypeSelect(menuCode,"");
//        List<AppTypeDetailEntity> doingList = pse.getDoingList();
//        request.getRequestDispatcher(doingList.get(0).getDetailValue()).forward(request,response);
//        return;
//    }
    CoreMenuTreeInfoEntity menuTree = Context.getCurrent().getMenuTree(menuCode);
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

        <script src="/public/camera/camera.js" type="text/javascript"></script>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <h4>这里是首页</h4>
        <div id="camera">
            <div id="contentHolder">

            </div>
            <div id="btn_snap">拍照</div>
        </div>
        <script type="text/javascript">
            $(function(){
//                util.masterpage a = new Camera({id: "contentHolder"});
            });

        </script>

    </master:Content>
</master:ContentPage>
