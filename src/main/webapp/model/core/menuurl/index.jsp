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
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree();
    String menuCode = Context.getMenuCode();
    String title = menuTree.getTitle();
%>

<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">

        <link rel="stylesheet" href="/model/core/menuurl/res/index.css"/>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <table id="mainTable" lay-filter="test"></table>

        <!-- 新增/修改 弹出框 -->
        <div class="layui-card my-model" style="display: none;">
            <form class="layui-form">
                <div class="layui-card-body">
                    <input type="hidden" name="urlId"/>
                    <div class="layui-row layui-col-space10">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">应用编码：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="code" required  lay-verify="required" placeholder="请输入应用编码"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">应用名称：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="title" required  lay-verify="required" placeholder="请输入应用名称"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">应用路径：</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" name="url" required  lay-verify="required" placeholder="请输入应用路径"/>
                        </div>
                    </div>
                </div>
                <div class="layui-card-footer">
                    <button class="layui-btn" lay-submit lay-filter="formDemo">保存</button>
                    <button class="layui-btn layui-btn-primary layui-layer-close" type="button">关闭</button>
                </div>
            </form>
        </div>

        <!-- 搜索 弹出框 只需要改layui-card-body里面的内容即可，查询和关闭按钮已自动集成 -->
        <div class="layui-card search-model" style="display: none;">
            <form class="layui-form">
                <div class="layui-card-body">
                    <div class="layui-form-item">
                        <label class="layui-form-label">关键字：</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" name="searchKey" placeholder="请输入关键字……"/>
                        </div>
                    </div>
                </div>
            </form>
        </div>

        <script type="text/javascript" src="/model/core/menuurl/res/index.js"></script>
    </master:Content>
</master:ContentPage>
