<%@ page import="model.core.menutree.entity.CoreMenuTreeInfoEntity" %>
<%@ page import="util.context.Context" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="master" uri="util.masterpage" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    CoreMenuTreeInfoEntity menuTree = Context.getMenuTree();
    String menuCode = Context.getMenuCode();
    String title = menuTree.getTitle();
    String memberId = Context.getMember().getMemberId();
%>

<master:ContentPage>
    <master:Content contentPlaceHolderId="title"><%=title%></master:Content>
    <master:Content contentPlaceHolderId="head">
        <link rel="stylesheet" href="/model/core/memberarchives/res/index.css"/>
    </master:Content>
    <master:Content contentPlaceHolderId="body">
        <div class="layui-card">
            <div class="layui-card-body">
                <div class="layui-row layui-col-space10">
                    <div class="layui-col-xs12 layui-col-sm12 layui-col-md2 layui-col-md-offset3 div-left-item">
                        <div class="member-photo">
                            <img src="/core/memberArchives/getPhoto.do?memberId=<%=memberId%>">
                        </div>
                        <div class="update-photo">
                            <a id="test1" href="#">
                                <i class="layui-icon layui-icon-upload"></i>上传图片
                            </a>
                        </div>
                    </div>
                    <div class="layui-col-xs12 layui-col-sm12 layui-col-md4">
                        <from class="layui-form">
                            <input type="hidden" name="memberId"/>
                            <div class="layui-form-item">
                                <label class="layui-form-label">名称：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="memberName" required  lay-verify="required" placeholder="请输入名称" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">电话：</label>
                                <div class="layui-input-block">
                                    <input type="text" name="memberName" required  lay-verify="required" placeholder="请输入电话" class="layui-input">
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <label class="layui-form-label">性别：</label>
                                <div class="layui-input-block">
                                    <input type="checkbox" name="isShow" value="true" lay-skin="switch" lay-text="男|女">
                                </div>
                            </div>
                            <div class="layui-form-item save-div">
                                <div class="layui-input-block">
                                    <button class="layui-btn" lay-submit lay-filter="formDemo"><i class="layui-icon layui-icon-ok"></i>保存</button>
                                </div>
                            </div>
                        </from>
                    </div>
                </div>
            </div>
        </div>


        <script type="text/javascript">
            var memberId = "<%=memberId%>";
        </script>
        <script type="text/javascript" src="/model/core/memberarchives/res/index.js"></script>
    </master:Content>
</master:ContentPage>
