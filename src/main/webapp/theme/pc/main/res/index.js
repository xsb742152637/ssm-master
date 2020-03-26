layui.use(['element','layer'], function(){
    var $ = layui.jquery;
    var element = layui.element;
    var layer = layui.layer;

    layer.config({
        anim: 5, //默认动画风格
        skin: 'layui-layer-molv'
    });

    //加载菜单
    !function() {
        $.ajax({
            url: "/core/menuTree/getMenuTree.do",
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data != null && data.length > 0) {
                    var parentE = $('ul[lay-filter="layadmin-system-side-menu"]');
                    parentE.empty();
                    _loadMenuTree(parentE, data,true);
                }

                $(".layui-nav-bar").attr("class", "zorder:999;");//解决左侧菜单hover失效问题
                //重新渲染菜单
                element.render();
                element.init();
                menu_set();
            },
            error: function (jqXHR) {
                console.log(jqXHR);
            }
        });
    }();

    element.on('nav(layadmin-system-side-menu)',function(){
        console.log('点击了')
    });

    //组装菜单
    var _loadMenuTree = function(parentE,data,isOne){
        var isHave = false;
        for(var i = 0 ; i < data.length ; i++){
            var d = data[i];
            if(d.menuId == thisMenuId)
                isHave = true;

            var li;
            if(isOne){
                li = $('<li/>').addClass('layui-nav-item' + (d.menuId == thisMenuId ? ' layui-this' : ''));
            }else{
                li = $('<dd/>').addClass((d.menuId == thisMenuId ? ' layui-this' : ''));
            }
            var aa = $('<a/>').attr('href',String.isNullOrWhiteSpace(d.url) ? 'javascript:;' : (d.url + '?menuCode=' + d.code)).attr("lay-tips",d.title).attr("lay-direction","1");
            aa.append($("<i/>").addClass("layui-icon " + (String.isNullOrWhiteSpace(d.icon) ? 'layui-icon-file' : d.icon)));
            aa.append($("<cite/>").text(d.title));
            li.append(aa);
            if(d.children != null && d.children.length > 0){
                var dl = $("<dl/>").addClass("layui-nav-child");
                var c_isHave = _loadMenuTree(dl,d.children,false);
                if(c_isHave){
                    isHave = c_isHave;
                    li.addClass('layui-nav-itemed');
                }
                dl.appendTo(li);
            }
            li.appendTo(parentE);
        }
        return isHave;
    }


    //左侧菜单收缩与展开
    var menu_set = function(){
        $("#LAY_app_flexible").on("click",function(){
            var c = "layadmin-side-shrink";
            var t = $(".layadmin-tabspage-none");
            if(t.hasClass(c)){//菜单展开
                t.removeClass(c);
                $(this).attr("class","layui-icon layui-icon-shrink-right");
                $(".logo-text").hide();
            }else{
                t.addClass(c);
                $(this).attr("class","layui-icon layui-icon-spread-left");
                $(".logo-text").show();
                //
                var tip_index = 0;
                $('#LAY-system-side-menu li').off('mouseenter').off('mouseleave').on('mouseenter', function(){
                    var that = this;
                    tip_index = layer.tips($($(that).find('a')[0]).text(), that, {time: 0});
                }).on('mouseleave', function(){
                    layer.close(tip_index);
                });
            }
        });
    };
});


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