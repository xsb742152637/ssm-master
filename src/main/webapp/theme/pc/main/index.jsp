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
    String memberName = "";
    if(Context.getMember() != null){
        memberName = Context.getMember().getMemberName();
    }
%>
<html>
<head>
    <title>
        <master:ContentPlaceHolder id="title"/>
    </title>

    <link rel="stylesheet" href="/public/jquery/easyui-1.7.0/themes/default/easyui.css"/>
    <%--<link rel="stylesheet" href="/public/jquery-easyui-1.7.0/themes/black/easyui.css"/>--%>
    <link rel="stylesheet" href="/public/jquery/easyui-1.7.0/themes/icon.css"/>
    <link rel="stylesheet" href="/theme/pc/main/res/index.css"/>

    <script type="text/javascript" src="/public/jquery/easyui-1.7.0/jquery.min.js"></script>
    <script type="text/javascript" src="/public/jquery/easyui-1.7.0/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/public/util/eiis.foundation.js"></script>
    <script type="text/javascript" src="/theme/pc/main/res/index.js"></script>

    <master:ContentPlaceHolder id="head"/>

</head>
<body class="easyui-layout">
    <div class="my-panel-top" data-options="region:'north',border:false,collapsible:false" style="height:50px;">
        <div style="text-align: right">
            <span><%=memberName%></span>
            <button onclick="loginout()">退出</button>
        </div>
    </div>
    <div class="my-panel-left" data-options="region:'west',collapsible:false,title:'<div><div>菜单</div><div class=\'panel-tool\'><a href=\'javascript:;\' class=\'layout-button-left\'></a></div></div>'" style="border-left: none;border-bottom: none;">
        <div class="easyui-sidemenu my-menu" data-options="border:false,multiple:false,data:[]"></div>
    </div>
    <div class="my-panel-center" data-options="region:'center'" style="border-right: none;border-bottom: none;">
        <master:ContentPlaceHolder id="body"/>
    </div>

    <script type="text/javascript">
//        var data = [{
//            text: '消费单',
//            iconCls: 'icon-sum',
//            state: 'open',
//            children: [{
//                text: 'Option1'
//            },{
//                text: 'Option2'
//            },{
//                text: 'Option3',
//                children: [{
//                    text: 'Option31'
//                },{
//                    text: 'Option32'
//                }]
//            }]
//        },{
//            text: 'Item2',
//            iconCls: 'icon-more',
//            children: [{
//                text: 'Option4'
//            },{
//                text: 'Option5'
//            },{
//                text: 'Option6'
//            }]
//        }];

        var leftMaxWeight = 200;
        var leftMinWeight = 60;
        $(function(){
            loadMenuTree();
            $('.my-panel-left').panel('resize', {width: leftMaxWeight});
            $('body').layout('resize');
            $(".my-panel-left").parent().find(".panel-header .panel-tool").on("click",function(){
                var opts = $('.my-menu').sidemenu('options');
                $('.my-menu').sidemenu(opts.collapsed ? 'expand' : 'collapse');
                opts = $('.my-menu').sidemenu('options');
                $('.my-menu').sidemenu('resize', {width: opts.collapsed ? leftMinWeight : leftMaxWeight});
                $('.my-panel-left').panel('resize', {width: opts.collapsed ? leftMinWeight : leftMaxWeight});
                $('body').layout('resize');
            });
        });

        function loadMenuTree(){
            $.ajax({
                url: "/core/menuTree/getMenuTree.do",
                dataType: 'json',
                type: "POST",
                success: function (data) {
                    console.log(data);
                    $('.my-menu').sidemenu({
                        data: data,
                        onSelect: function(item){
                            console.log("选择");
                            console.log(item);
                            window.location.href = item.url;
                        }
                    });
                },
                error: function (jqXHR) {
                    console.log(jqXHR);
                }
            });
        }
        function loginout(){
            $.ajax({
                url: "/anon/logoutServlet",
                dataType: 'text',
                type: "POST",
                success: function (data) {
                    window.location.reload();
                },
                error: function (jqXHR) {
                    console.log(jqXHR);
                }
            });
        }
    </script>
</body>
</html>
