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
    String menuCode = request.getParameter("menuCode");
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree(menuCode);
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
                        <button type="button" class="layui-btn btn-add">
                            <i class="layui-icon layui-icon-add-1"></i>新增下级人员
                        </button>
                        <button type="button" class="layui-btn btn-del layui-bg-red">
                            <i class="layui-icon layui-icon-delete"></i>删除当前人员
                        </button>
                    </div>
                    <div class="layui-card-body">
                        <from class="layui-form">
                            <input type="hidden" name="memberId"/>
                            <input type="hidden" name="parentId"/>
                            <div class="layui-form-item">
                                <label class="layui-form-label">用户名称：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="memberName" required  lay-verify="required" placeholder="请输入用户名称" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">用户类型：</label>
                                <div class="layui-input-block">
                                    <select name="memberType" lay-search>
                                        <option value="">请选择用户类型 </option>
                                        <%=memberType%>
                                    </select>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">用户账户：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="account" required  lay-verify="required" placeholder="请输入用户账户" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">用户密码：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="password" required  lay-verify="required" placeholder="请输入用户密码" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">用户状态：</label>
                                <div class="layui-input-block">
                                    <input type="checkbox" name="isFrozen" value="true" lay-skin="switch" lay-text="启用|停用">
                                </div>
                            </div>
                            <div class="layui-form-item">
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
            var treeType = "<%=TreeType.MemberInfo.getCode()%>";
        </script>
        <script type="text/javascript" src="/model/core/member/res/index.js"></script>
    </master:Content>
</master:ContentPage>
