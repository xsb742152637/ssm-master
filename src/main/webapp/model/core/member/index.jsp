<%@ page import="util.context.Context" %>
<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %><%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2020/3/30
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="util.context.Context" %>
<%@ page import="model.core.memberinfo.MemberType" %>
<%@ page import="model.core.treeinfo.TreeType" %>
<%@ taglib prefix="master" uri="util.masterpage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree();
    String menuCode = Context.getMenuCode();
    String title = menuTree.getTitle();
    String memberType = MemberType.getNames();
%>

<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">
        <link rel="stylesheet" href="/model/core/member/res/index.css"/>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <div class="layui-row layui-col-space10">
            <div class="layui-col-xs12 layui-col-sm6 layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-header">
                        <!-- 按钮栏 -->
                        <button type="button" class="layui-btn btn-move" data-type="true">
                            <i class="layui-icon layui-icon-up" style="margin-right: 0px;"></i>
                        </button>
                        <button type="button" class="layui-btn btn-move" data-type="false">
                            <i class="layui-icon layui-icon-down" style="margin-right: 0px;"></i>
                        </button>
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
                        <button type="button" class="layui-btn btn-add" data-type="<%=MemberType.Group.getCode().toString()%>">
                            <i class="layui-icon layui-icon-group"></i>新增群组
                        </button>
                        <button type="button" class="layui-btn btn-add" data-type="<%=MemberType.Person.getCode().toString()%>">
                            <i class="layui-icon layui-icon-username"></i>新增成员
                        </button>
                        <button type="button" class="layui-btn btn-del">
                            <i class="layui-icon layui-icon-delete"></i>删除
                        </button>
                    </div>
                    <div class="layui-card-body">
                        <from class="layui-form">
                            <input type="hidden" name="memberId"/>
                            <input type="hidden" name="parentId"/>
                            <input type="hidden" name="memberType"/>
                            <div class="div-items">
                                <div class="msg-item">请选择一个节点</div>
                            </div>
                            <div class="layui-form-item save-div" style="display: none;">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit lay-filter="formDemo"><i class="layui-icon layui-icon-ok"></i>保存</button>
                                </div>
                            </div>
                        </from>
                    </div>
                </div>
            </div>
            <div></div>
        </div>

        <script type="text/javascript">
            var rootId = "<%=TreeType.MemberInfo.toString()%>";
            var treeType = "<%=TreeType.MemberInfo.getCode()%>";
            var memberType = <%=MemberType.Person.getCode()%>;
        </script>
        <script type="text/javascript" src="/model/core/member/res/index.js"></script>
    </master:Content>
</master:ContentPage>
