$(function(){
    // 去掉所有input的autocomplete, 显示指定的除外 
    $('input:not([autocomplete]),textarea:not([autocomplete]),select:not([autocomplete])').attr('autocomplete', 'off');
});
layui.use(['element','layer'], function(){
    let $ = layui.jquery;
    let element = layui.element;
    let layer = layui.layer;

    //加载菜单
    !function() {
        $.ajax({
            url: "/core/menuTree/getMenuTree.do",
            data:{needGuide: !isAdmini,isTop: false,isShow: true},//区分权限,不显示顶级节点，不显示隐藏节点
            dataType: 'json',
            type: "POST",
            success: function (data) {
                if (data != null && data.length > 0) {
                    let parentE = $('ul[lay-filter="layadmin-system-side-menu"]');
                    parentE.empty();
                    _loadMenuTree(parentE, data,0);
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

    // element.on('nav(layadmin-system-side-menu)',function(){
    //     console.log('点击了菜单')
    // });

    //组装菜单
    let mbx = '';//面包屑
    let _loadMenuTree = function(parentE,data,c){
        c++;//菜单层次
        let isHave = false;
        for(let i = 0 ; i < data.length ; i++){
            let isHave2 = false;
            let d = data[i];

            //无权限的菜单不加载
            if(!d.isHaveGuide){
                console.log(d.title + ' 无权限');
                continue;
            }

            if(d.menuId == thisMenuId)
                isHave2 = true;

            let li;
            if(c == 1){
                li = $('<li/>').addClass('layui-nav-item' + (d.menuId == thisMenuId ? ' layui-this' : ''));
            }else{
                let pl = (c - 1) * 20;
                if(pl > 80){
                    pl = 80;
                }
                li = $('<dd/>').addClass((d.menuId == thisMenuId ? ' layui-this' : '')).css({'padding-left': (pl + 'px')});
            }
            let aa = $('<a/>').attr('href',String.isNullOrWhiteSpace(d.url) ? 'javascript:;' : (d.url + '?menuCode=' + d.code)).attr("lay-tips",d.title).attr("lay-direction","1");
            aa.append($("<i/>").addClass("layui-icon " + (String.isNullOrWhiteSpace(d.icon) ? 'layui-icon-file' : d.icon)));
            aa.append($("<cite/>").text(d.title));
            li.append(aa);
            if(d.children != null && d.children.length > 0){
                let dl = $("<dl/>").addClass("layui-nav-child");
                let c_isHave = _loadMenuTree(dl,d.children,c);
                if(c_isHave){
                    isHave2 = c_isHave;
                    li.addClass('layui-nav-itemed');
                }
                dl.appendTo(li);
            }

            if(isHave2){
                isHave = isHave2;
                mbx = '<a><i class="layui-icon '+ (String.isNullOrWhiteSpace(d.icon) ? 'layui-icon-file' : d.icon) +'"></i>'+ d.title +'</a>' + mbx;
                $('.layui-layout-left .layui-breadcrumb').empty().append(mbx);
            }
            li.appendTo(parentE);
        }
        return isHave;
    };

    //左侧菜单收缩与展开
    let menu_set = function(){
        let isOpenMenu = true;
        $(".LAY_app_flexible").on("click",function(){
            let c = "layadmin-side-shrink";
            let t = $(".layadmin-tabspage-none");
            if(t.hasClass(c)){//菜单展开
                t.removeClass(c);
                $(this).find('i').attr("class","layui-icon layui-icon-shrink-right");
                $(".logo-text").hide();
                isOpenMenu = true;
            }else{
                t.addClass(c);
                $(this).find('i').attr("class","layui-icon layui-icon-spread-left");
                $(".logo-text").show();
                isOpenMenu = false;
            }
        });

        let tip_index = 0;
        $('#LAY-system-side-menu li').on('mouseenter', function(){
            if(isOpenMenu)
                return;
            let that = this;
            tip_index = layer.tips($($(that).find('a')[0]).text(), that, {time: 0});
        }).on('mouseleave', function(){
            layer.close(tip_index);
        });
    };

    //esc 按钮关闭所有弹出框
    $(document).keyup(function(event){
        if(event.keyCode == 27 || event.keyCode == 96){
            console.log("esc关闭弹框：" + event.keyCode)
            layer.closeAll();
        }
    });
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