<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
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
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree();
    String menuCode = Context.getMenuCode();
    String title = menuTree.getTitle();
%>

<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">

        <link rel="stylesheet" href="/model/core/menutree/res/index.css"/>
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
                            <i class="layui-icon layui-icon-add-1"></i>新增菜单
                        </button>
                        <button type="button" class="layui-btn btn-del">
                            <i class="layui-icon layui-icon-delete"></i>删除菜单
                        </button>
                    </div>
                    <div class="layui-card-body">
                        <from class="layui-form">
                            <input type="hidden" name="menuId"/>
                            <input type="hidden" name="parentId"/>
                            <div class="layui-form-item">
                                <label class="layui-form-label">应用：</label>
                                <div class="layui-input-block">
                                    <!-- lay-verify="required" 表示验证类型为必填 -->
                                    <!-- lay-search 表示开启搜索 -->
                                    <select name="urlId" lay-search lay-filter="urlSel">
                                        <option value="" >请选择一个应用</option>
                                    </select>
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <label class="layui-form-label">菜单名称：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="title" required  lay-verify="required" placeholder="请输入菜单名称" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">菜单图标：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="icon"  placeholder="请输入菜单图标" class="layui-input">
                                    <i class="layui-icon layui-icon-more-vertical"></i>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">菜单状态：</label>
                                <div class="layui-input-block">
                                    <input type="checkbox" name="isShow" value="true" lay-skin="switch" lay-text="启用|停用">
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
        </div>

        <script type="text/javascript" src="/model/core/menutree/res/index.js"></script>
    </master:Content>
</master:ContentPage>
