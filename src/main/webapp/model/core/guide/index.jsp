<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %>
<%@ page import="model.core.treeinfo.TreeType" %>
<%@ page import="model.core.guide.GuideType" %>
<%@ page import="model.core.guide.service.core.Member" %>
<%@ page import="model.core.guide.service.core.Menu" %>
<%@ page import="model.core.memberinfo.MemberType" %>
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

        <link rel="stylesheet" href="/model/core/guide/res/index.css"/>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <input type="hidden" id="projectId" value="project"/>
        <div class="layui-row layui-col-space10">
            <div class="layui-col-xs12 layui-col-sm6 layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-header">

                    </div>
                    <div class="layui-card-body">
                        <div id="my-tree"></div>
                    </div>
                </div>
            </div>
            <div class="layui-col-xs12 layui-col-sm6 layui-col-md8">
                <div class="layui-card">
                    <div class="layui-card-header">
                        <!-- 按钮栏 -->
                        <button type="button" class="layui-btn btn-save">
                            <i class="layui-icon layui-icon-add-1"></i>保存
                        </button>
                    </div>
                    <div class="layui-card-body">
                        <div class="layui-row layui-col-space10">
                            <div id="guide_menu_tree" class="layui-col-xs12 layui-col-sm6 layui-col-md6"></div>
                            <div id="menuDebugTree" class="layui-col-xs12 layui-col-sm6 layui-col-md6"></div>
                        </div>

                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            var treeType = "<%=TreeType.MemberInfo.getCode()%>";
            var titleRead = "<%=GuideType.Read.getName().toString()%>";
            var titleReadSelf = "<%=GuideType.ReadSelf.getName().toString()%>";
            var titleUpdate = "<%=GuideType.Update.getName().toString()%>";

            var codeRead = "<%=GuideType.Read.getCode().toString()%>";
            var codeReadSelf = "<%=GuideType.ReadSelf.getCode().toString()%>";
            var codeUpdate = "<%=GuideType.Update.getCode().toString()%>";

            var memberTag = "<%=Member.MEMBER_TAG%>";
            var menuTag = "<%=Menu.MENU_TAG%>";

            var personType = <%=MemberType.Person.getCode()%>;
        </script>
        <script type="text/javascript" src="/model/core/guide/res/config.js"></script>
        <script type="text/javascript" src="/model/core/guide/res/comm.js"></script>
        <script type="text/javascript" src="/model/core/guide/res/member.js"></script>
        <script type="text/javascript" src="/model/core/guide/res/menu.js"></script>
        <script type="text/javascript" src="/model/core/guide/res/menuEx.js"></script>

        <script type="text/javascript" src="/model/core/guide/res/index.js"></script>
    </master:Content>
</master:ContentPage>
