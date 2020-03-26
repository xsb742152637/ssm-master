//JavaScript代码区域
layui.use(['element'], function(){
    var $ = layui.jquery;
    var element = layui.element;

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
        }
    });

    $.ajax({
        url: "/core/menuTree/getMenuTree.do",
        dataType: 'json',
        type: "POST",
        success: function (data) {
            if(data != null && data.length > 0){
                var parentE = $('ul[lay-filter="layadmin-system-side-menu"]');
                var a = 0;
                _loadMenuTree(parentE,data,a);

            }
            console.log(data);

            element.render('nav');
        },
        error: function (jqXHR) {
            console.log(jqXHR);
        }
    });

    element.on('nav(layadmin-system-side-menu)',function(){
        console.log('点击了')
    });

});

function _loadMenuTree(parentE,data,a){
    for(var i = 0 ; i < data.length ; i++){
        var d = data[i];

        var li = $('<li></li>').addClass('layui-nav-item' + (a == 0 ? ' layui-nav-itemed' : '') + (d.menuId == thisMenuId ? ' layui-this' : ''));
        var aa = $('<a/>').attr('href',String.isNullOrWhiteSpace(d.url) ? 'javascript:;' : d.url).attr("lay-tips",d.title).attr("lay-direction","2");
        aa.append($("<i/>").addClass("layui-icon layui-icon-home"));
        aa.append($("<cite/>").text(d.title));
        li.append(aa);
        a++;
        if(d.children != null && d.children.length > 0){
            var dl = $("<dl/>").addClass("layui-nav-child");
            _loadMenuTree(dl,d.children);
            dl.appendTo(li);
        }
        li.appendTo(parentE);
    }
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