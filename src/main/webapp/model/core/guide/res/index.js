var selData = null;//菜单树选中的信息
var isUpdate = false;//是否有未保存的修改

layui.use(['tree','layer','form'], function(){
    let tree = layui.tree, form = layui.form,layer = layui.layer;
    let treeId = 'myTree';

    tree.render({
        id: treeId,
        elem: '#my-tree',  //绑定元素
        url: "/core/treeinfo/getMainInfo.do",
        where: {treeType: treeType,openLevel: -1},//展开全部
        accordion: false,//开启手风琴模式
        onlyIconControl: true,//是否仅允许节点左侧图标控制展开收缩
        selectedId: (selData == null ? '' : selData.treeId),//默认选中
        loadSuccess: function(e){
            $(e.elem).find('.layui-tree-set').each(function(){
                let data = $(this).data('data');
                //停用的标签暗字显示
                if(data != null && !data.isShow){
                    $(this).find('.layui-tree-txt:first').addClass('layui-tree-color2');
                }
            });
        },
        click: function(e) { //节点选中状态改变事件监听，全选框有自己的监听事件
            if(e.selected){
                selData = e.data;
                if(Boolean.parse(selData.isFrozen)){
                    layer.msg('当前成员已冻结，无法授权');
                }else{
                    checkMem();
                }
                // tree_edit();
            }else{
                selData = null;
            }
        }
    });
    //加载树
    let loadTree = function(){
        tree.reload(treeId,{selectedId: (selData == null ? '' : selData.treeId)});
    };

    //保存
    $('.btn-save').on('click',function(){
        let root = Menu.getRoot();
        layer.load();
        $.ajax({
            url: "/core/guide/saveGuide.do",
            dataType: 'json',
            type: "POST",
            data: {projectId: $("#projectId").val(),str: $(root).prop("outerHTML")},
            success: function (rs) {
                isUpdate = false;
                layer.res(rs);
                layer.close();
                if(!rs.error){
                    if(rs.msg.indexOf("重新加载") >= 0){
                        setTimeout(function(){
                            location.reload();
                        },3000)
                    }
                }
            },
            error: function (jqXHR) {
                layer.close();
                console.log(jqXHR);
            }
        });
    });
});

$(function(){
    member_init();
    menu_init();
    menu_debug_init();
});

var menu_init = function(){
    let doc = Menu.transform();
    $("#guide_menu_tree").html(doc);
    $("#guide_menu_tree .layui-tree-other").each(function(i,o){
        let id = $(this).parent().attr("id");

        $(this).append('<div class="check-div"><span id="img_'+codeUpdate+'_'+id+'" class="checkImg layui-icon layui-icon-circle" onclick="checkbox_action(this,\''+codeUpdate+'\')" title="'+titleUpdate+'"></span></div>');
        $(this).append('<div class="check-div"><span id="img_'+codeRead+'_'+id+'" class="checkImg layui-icon layui-icon-circle" onclick="checkbox_action(this,\''+codeRead+'\')" title="'+titleRead+'"></span></div>');
        $(this).append('<div class="check-div"><span id="img_'+codeReadSelf+'_'+id+'" class="checkImg layui-icon layui-icon-circle" onclick="checkbox_action(this,\''+codeReadSelf+'\')" title="'+titleReadSelf+'"></span></div>');
    });

    let str = '<div style="width: 100%" class="tree-table"><div class="three-th">菜单名称</div>';
    str += '<div class="two-th" style="color: #eea236">'+titleUpdate+'</div>'
    str += '<div class="first-th" style="color: blue">'+titleRead+'</div>';
    str += '<div class="first-th" style="color: #7676f9">'+titleReadSelf+'</div>';
    str += '</div>';
    $("#guide_menu_tree").prepend(str);
    $('#guide_menu_tree .layui-tree-iconClick>i').off('click').on('click',function(){
        let entry = $(this).parents(".layui-tree-entry");
        console.log($(this).attr('class'));
        console.log($(this).is('.layui-icon-addition'));
        if($(this).is('.layui-icon-addition')){
            $(this).removeClass('layui-icon-addition').addClass('layui-icon-subtraction');
            entry.next().show();
        }else{
            $(this).removeClass('layui-icon-subtraction').addClass('layui-icon-addition');
            entry.next().hide();
        }

    });

    setMenuTitle();

    $('#guide_menu_tree .layui-tree-iconClick:first-child>i').trigger('click');
};

var setMenuTitle = function(ids){
    $("#guide_menu_tree .menu_mems").each(function(i,o){
        var menuId = $(o).prev().attr('id');
        if(ids == null || ids.length == 0 || ids.contains(menuId)){
            var c = $(o).find('.menu_mems_s>span').length + $(o).find('.menu_mems_r>span').length + $(o).find('.menu_mems_u>span').length;

            if(c > 0){
                var str = '';
                str += titleReadSelf + '：' + getText($(o).find('.menu_mems_s').text()) + '\n';
                str += titleRead + '：' + getText($(o).find('.menu_mems_r').text()) + '\n';
                str += titleUpdate + '：' + getText($(o).find('.menu_mems_u').text()) + '\n';
                $(o).attr('title', str);
                $(o).next().html('等共'+ c +'项')
            }else{
                $(o).attr('title', '');
                $(o).next().html('')
            }
        }
    });
};
var getText = function(t){
    return t.replaceAll('\n','').replaceAll(' ','');
};

var member_init = function(){
    let doc = Mem.transform();
};

var getMemberId = function(){
    let memberId = selData.memberId;
    if(selData.memberType == personType){
        memberId += ";" + selData.parentId;
    }
    return memberId;
};

//选中一个成员时，切换菜单树
var checkMem = function(){
    if(selData == null){
        layer.msg("没有找到xMem节点");
        return;
    }
    let xMem = Mem.getItem(getMemberId());
    if (xMem == null) {
        layer.msg("没有找到xMem节点");
        return;
    }
    layer.load();
    setTimeout(function(){
        MenuEx.setAuth(Menu.getRoot(), xMem,codeReadSelf);

        MenuEx.setAuth(Menu.getRoot(), xMem,codeRead);

        MenuEx.setAuth(Menu.getRoot(), xMem,codeUpdate);
        layer.close();
    },500);

};

var menu_debug_init = function() {
    let xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(Comm.load("./transform/app_debug.xsl"));
    let appDocument1 = xsltProcessor.transformToFragment(Menu.getContext(), document);
    $("#menuDebugTree").html(appDocument1);
};

//勾选
var checkbox_action = function(obj,type) {
    // let a = (new Date()).getTime();
    let appId = $(obj).attr('id').substr(6);

    let img = $(obj).attr('class');

    if (selData == null) {
        layer.msg('请选择一个成员');
        return;
    }
    let xMem = Mem.getItem(getMemberId());
    if (xMem == null) {
        layer.msg("没有找到："+selData.memberId);
        return;
    }

    let xMenu = Menu.getItem(appId);
    if (xMenu == null) {
        layer.msg("没有找到xMenu节点");
        return;
    }

    // $.message.loader.open("正在调整勾选情况……");
    // setTimeout(function(){
    // let isAutoCheck = false;//是否自动勾选了另外一个权限
    let type2 = "";
    if (img == MenuEx.getUncheck() || img == MenuEx.getCheckMix()) {
        MenuEx.select(xMem, xMenu,type);
        // 勾选编辑权限，判断查看权限是否全选，如果不是，则自动勾选查看权限
        // let r = $("#img_r_"+appId).attr('class');
        // if(type == codeUpdate && r != MenuEx.getCheck()){
        //     MenuEx.select(xMem, xMenu,codeRead);
        //     isAutoCheck = true;
        //     type2 = codeRead;
        // }
    }else {
        MenuEx.reverseSelect(xMem, xMenu,type);
        // let u = $("#img_u_"+appId).attr('class');
        // //取消查看权限，判断编辑权限是否勾选，只要不是未选，都自动取消勾选
        // if(type == codeRead && u != MenuEx.getUncheck()){
        //     MenuEx.reverseSelect(xMem, xMenu,codeUpdate);
        //     isAutoCheck = true;
        //     type2 = codeUpdate;
        // }
    }
    // console.log("调整成员完成，耗时："+((new Date()).getTime()-a));
    // a = (new Date()).getTime();
    MenuEx.setAuth(Menu.getRoot(), xMem,type);
    // if(isAutoCheck)
    //     MenuEx.setAuth(Menu.getRoot(), xMem,type2);

    // console.log("调整勾选状态完成，耗时："+((new Date()).getTime()-a));
    menu_debug_init();
    isUpdate = true;
    //     $.message.loader.close();
    // },500);
};
