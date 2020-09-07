<%@ page import="util.context.Context" %>
<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %><%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2020/4/7
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="maste" uri="util.masterpage"%>
<%
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree();
    String menuCode = Context.getMenuCode();
    String title = menuTree.getTitle();
%>

<maste:ContentPage>
    <maste:Content contentPlaceHolderId="title"><%=title%></maste:Content>
    <maste:Content contentPlaceHolderId="head">

    </maste:Content>
    <maste:Content contentPlaceHolderId="body">
        <div class="layui-fluid">
            <div class="layui-row layui-col-space10">
                <div class="ayui-col-xs12 layui-col-sm6 layui-col-md4">
                    <div class="layui-card">
                        <table id="mainTable" lay-filter="main"></table>
                    </div>
                </div>
                <div class="layui-col-xs12 layui-col-sm6 layui-col-md8">
                    <div class="layui-card">
                        <table id="detailTable" lay-filter="det"></table>
                    </div>
                </div>
            </div>
        </div>

        <!-- 新增/修改 弹出框 -->
        <div class="layui-card main-model" style="display: none;">
            <form class="layui-form">
                <div class="layui-card-body">
                    <input type="hidden" name="dicId"/>
                    <div class="layui-row layui-col-space10">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">字典编码：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="dicCode" required  lay-verify="required" placeholder="请输入字典编码"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">字典名称：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="dicName" required  lay-verify="required" placeholder="请输入字典名称"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">字典描述：</label>
                        <div class="layui-input-block">
                            <input type="text" class="layui-input" name="dicDes"  placeholder="选填"/>
                        </div>
                    </div>
                </div>
                <div class="layui-card-footer">
                    <button class="layui-btn" lay-submit lay-filter="saveMain">保存</button>
                    <button class="layui-btn layui-btn-primary layui-layer-close" type="button">关闭</button>
                </div>
            </form>
        </div>

        <!-- 新增/修改 弹出框 -->
        <div class="layui-card detail-model" style="display: none;">
            <form class="layui-form">
                <div class="layui-card-body">
                    <input type="hidden" name="detailId"/>
                    <input type="hidden" name="dicId"/>
                    <div class="layui-row layui-col-space10">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">编码：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="detailCode" required  lay-verify="required" placeholder="请输入编码"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">标题：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="detailName" required  lay-verify="required" placeholder="请输入标题"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space10">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">值：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="detailValue" placeholder="选填"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">顺序：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="detailLevel" lay-verify="number" placeholder="选填"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="layui-row layui-col-space10">
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">备注：</label>
                                <div class="layui-input-block">
                                    <input type="text" class="layui-input" name="comment" placeholder="选填"/>
                                </div>
                            </div>
                        </div>
                        <div class="layui-col-xs12 layui-col-sm6 layui-col-md6">
                            <div class="layui-form-item">
                                <label class="layui-form-label">状态：</label>
                                <div class="layui-input-block">
                                    <input type="checkbox" name="isValid" value="true" lay-skin="switch" lay-text="启用|停用">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layui-card-footer">
                    <button class="layui-btn" lay-submit lay-filter="saveDetail">保存</button>
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
        <script type="text/javascript" src="res/index.js"></script>
    </maste:Content>
</maste:ContentPage>
