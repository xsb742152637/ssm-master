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

        <link rel="stylesheet" href="/model/core/menutree/res/index.css"/>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <div class="layui-row layui-col-space10">
            <div class="layui-col-xs12 layui-col-sm6 layui-col-md4">
                <div class="layui-card">
                    <div class="layui-card-header">
                        <!-- 按钮栏 -->
                        <button type="button" class="layui-btn layui-btn-sm btn-move" data-type="true"><i class="layui-icon layui-icon-up"></i>上移</button>
                        <button type="button" class="layui-btn layui-btn-sm btn-move" data-type="false"><i class="layui-icon layui-icon-down"></i>下移</button>
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
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-normal btn-add"><i class="layui-icon layui-icon-add-1"></i>新增下级菜单</button>
                        <button type="button" class="layui-btn layui-btn-sm layui-btn-danger btn-del"><i class="layui-icon layui-icon-delete"></i>删除当前菜单</button>
                    </div>
                    <div class="layui-card-body">
                        <from class="tree-form">
<%--                            <input type="hidden" name="type"/>--%>
<%--                            <input type="hidden" name="menuId"/>--%>
                            <div class="layui-form-item">
                                <label class="layui-form-label">应用：</label>
                                <div class="layui-input-block">
                                    <!-- lay-verify="required" 表示验证类型为必填 -->
                                    <!-- lay-search 表示开启搜索 -->
                                    <select name="urlId" lay-verify="required" required lay-search>
                                        <option value="" style="display: none;">请选择一个应用</option>
                                        <option value="010">北京</option>
                                        <option value="021">上海</option>
                                        <option value="0571">杭州</option>
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
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">菜单状态：</label>
                                <div class="layui-input-block">
                                    <input type="checkbox" name="isShow" lay-skin="switch" lay-text="启用|停用">
                                </div>
                            </div>

                            <div class="layui-form-item">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit lay-filter="formDemo">保存</button>
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