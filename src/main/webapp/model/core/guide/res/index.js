layui.use(['tree','layer','form'], function(){
    let tree = layui.tree, form = layui.form,layer = layui.layer;

    let selData = null;//菜单树选中的信息
    let isUpdate = false;//是否有未保存的修改
    //加载树
    let loadTree = function(){
        layer.load();
        $.ajax({
            url: "/core/treeinfo/getMainInfo.do",
            data: {treeType: treeType},
            dataType: 'json',
            type: "POST",
            success: function (data) {
                layer.close();
                if (data != null && data.length > 0) {
                    //渲染
                    let inst1 = tree.render({
                        id: 'myTree',
                        elem: '#my-tree',  //绑定元素
                        accordion: false,//开启手风琴模式
                        onlyIconControl: true,//是否仅允许节点左侧图标控制展开收缩
                        selectedId: (selData == null ? '' : selData.treeId),//默认选中
                        data: data,
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
                }
            },
            error: function (jqXHR) {
                layer.close();
                console.log(jqXHR);
            }
        });
    };
    loadTree();

    let member_init = function(){
        let doc = Mem.transform();
    };

    let menu_init = function(){
        let doc = Menu.transform();
        $("#guide_menu_tree").html(doc);
        $("#guide_menu_tree .tree-node").each(function(i,o){
            let id = $(this).find(".tree-title>span").attr("id");

            $(this).append('<div class="check-div"><span id="img_'+codeUpdate+'_'+id+'" class="checkImg tree-icon  uncheck" onclick="checkbox_action(this,\''+codeUpdate+'\')" title="'+titleUpdate+'"></span></div>');

            $(this).append('<div class="check-div"><span id="img_'+codeRead+'_'+id+'" class="checkImg tree-icon  uncheck" onclick="checkbox_action(this,\''+codeRead+'\')" title="'+titleRead+'"></span></div>');
            $(this).append('<div class="check-div"><span id="img_'+codeReadSelf+'_'+id+'" class="checkImg tree-icon  uncheck" onclick="checkbox_action(this,\''+codeReadSelf+'\')" title="'+titleReadSelf+'"></span></div>');
        });

        let str = '<div style="width: 100%" class="tree-table"><div class="three-th">菜单名称</div>';
        str += '<div class="two-th">'+titleUpdate+'</div>'
        str += '<div class="first-th">'+titleRead+'</div>';
        str += '<div class="first-th">'+titleReadSelf+'</div>';
        str += '</div>';
        $("#guide_menu_tree").prepend(str);
    };

    let getMemberId = function(){
        let memberId = selData.memberId;
        if(selData.memberType == personType){
            memberId += ";" + selData.parentId;
        }
        return memberId;
    };

    //勾选
    let checkbox_action = function(obj,type) {
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

    //选中一个成员时，切换菜单树
    let checkMem = function(){
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

    let menu_debug_init = function() {
        let xsltProcessor = new XSLTProcessor();
        xsltProcessor.importStylesheet(Comm.load("./transform/app_debug.xsl"));
        let appDocument1 = xsltProcessor.transformToFragment(Menu.getContext(), document);
        $("#menuDebugTree").html(appDocument1);
    };

    member_init();
    menu_init();
    menu_debug_init();
});