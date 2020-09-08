layui.use(['tree','layer','form'], function(){
    let tree = layui.tree, form = layui.form, layer = layui.layer;

    let canUpdate = true;
    //是否有编辑权限
    $.ajax({
        url: "/core/guide/canUpdate.do",
        dataType: 'text',
        type: "POST",
        async: false,
        success: function (rs) {
            if(!Boolean.parse(rs)){
                canUpdate = false;
                $('button').remove();
            }
        }
    });

    let selData = null;//菜单树选中的信息
    let treeId = 'myTree';
    //加载树
    tree.render({
        id: treeId,
        elem: '#my-tree',  //绑定元素
        url: "/core/treeinfo/getMainInfo.do",
        where: {treeType: treeType},
        // accordion: true,//开启手风琴模式
        onlyIconControl: true,//是否仅允许节点左侧图标控制展开收缩
        selectedId: (selData == null ? rootId : selData.treeId),//默认选中
        canCancelSelected: false, //不允许取消选中
        loadSuccess: function(e){
            $(e.elem).find('.layui-tree-set').each(function(){
                let data = $(this).data('data');
                //停用的标签暗字显示
                if(data != null && !data.memberState){
                    $(this).find('.layui-tree-txt:first').addClass('layui-tree-color2');
                }
            });
        },
        click: function(e) { //节点选中状态改变事件监听，全选框有自己的监听事件
            if(e.selected){
                selData = e.data;
                tree_edit();
            }else{
                selData = null;
                $('.div-items').empty().append('<div class="msg-item">请选择一个节点</div>');
                // Function.setForm($('.layui-form'),null,form);
            }
        }
    });

    let loadTree = function(){
        tree.reload(treeId,{selectedId: (selData == null ? '' : selData.treeId)});
    };

    let _check = function(){
        if(selData == null){
            layer.msg('请先选中一个菜单节点');
            return false;
        }
        return true;
    };

    //编辑
    let tree_edit = function(){
        if(!_check())
            return;
        layer.load();
        if(selData.memberType == memberType){
            $('.btn-add').hide();
        }else{
            $('.btn-add').show();
        }

        if(selData.canDelete){
            $('.btn-del').show();
        }else{
            $('.btn-del').hide();
        }

        load_item(selData);
        $.ajax({
            url: "/core/memberInfo/findOneByTreeId.do",
            dataType: 'json',
            type: "POST",
            data: {treeId: selData.treeId},
            success: function (rs) {
                layer.close();
                $.extend(selData, rs);
                Function.setForm($('.layui-form'),selData,form);
            },
            error: function (jqXHR) {
                layer.close();
                console.log(jqXHR);
            }
        });

    };

    let load_item = function(data){
        $('.div-items').empty();
        if(data.memberType == 2){
            $('.div-items').append('<div class="layui-form-item"><label class="layui-form-label">群组名称：</label><div class="layui-input-block"><input type="text" name="memberName" required  lay-verify="required" placeholder="请输入群组名称" class="layui-input" '+ (data.canUpdate ? '' : 'disabled="true"') +'></div></div>');
        }else{
            $('.div-items').append('<div class="layui-form-item"><label class="layui-form-label">用户名称：</label><div class="layui-input-block"><input type="text" name="memberName" required  lay-verify="required" placeholder="请输入用户名称" class="layui-input" '+ (data.canUpdate ? '' : 'disabled="true"') +'></div></div>');
            $('.div-items').append('<div class="layui-form-item"><label class="layui-form-label">用户账户：</label><div class="layui-input-block"><input type="text" name="account" required  lay-verify="required" placeholder="请输入用户账户" class="layui-input" '+ (data.canUpdate ? '' : 'disabled="true"') +'></div></div>');
            $('.div-items').append('<div class="layui-form-item"><label class="layui-form-label">用户密码：</label><div class="layui-input-block"><input type="text" name="password" required  lay-verify="required" placeholder="请输入用户密码" class="layui-input" '+ (data.canUpdate ? '' : 'disabled="true"') +'></div></div>');
            $('.div-items').append('<div class="layui-form-item"><label class="layui-form-label">用户状态：</label><div class="layui-input-block"><input type="checkbox" name="memberState" value="true" lay-skin="switch" lay-text="启用|停用" '+ (data.canUpdate ? '' : 'disabled="true"') +'></div></div>');
        }

        $('.div-items').append('<div class="layui-form-item"><label class="layui-form-label">是否可修改：</label><div class="layui-input-block"><input type="checkbox" name="canUpdate" value="true" lay-skin="switch" lay-text="是|否" '+ (data.canUpdate ? '' : 'disabled="true"') +'></div></div>');
        $('.div-items').append('<div class="layui-form-item"><label class="layui-form-label">是否可删除：</label><div class="layui-input-block"><input type="checkbox" name="canDelete" value="true" lay-skin="switch" lay-text="是|否" '+ (data.canUpdate ? '' : 'disabled="true"') +'></div></div>');
        if(data.canUpdate){
            $(".save-div").show();
        }else{
            $(".save-div").hide();
        }
    };
    //删除当前菜单
    $('.btn-del').on('click',function(){
        if(!_check())
            return;
        let d = $.extend({},selData);
        let isHave = false;
        let li = [];
        li.push(d);
        while (li.length > 0){
            let li2 = [];
            for(let i = 0 ; i < li.length ; i++){
                if(memberType == li[i].memberType){
                    isHave = true;
                    break
                }else if(li[i].children != null){
                    li2 = li2.concat(li[i].children);
                }
            }
            if(isHave){
                break;
            }
            li = li2;
        }
        if(isHave){
            layer.msg('当前群组下存在账号，不允许删除');
            return;
        }
        layer.confirm('确认删除当前群组及其下级？', {btn: ['确定','取消']},
            function(){
                layer.load();
                $.ajax({
                    url: "/core/memberInfo/deleteMain.do",
                    dataType: 'json',
                    type: "POST",
                    data: {treeId: selData.treeId,primaryId: selData.memberId},
                    success: function (rs) {
                        layer.res(rs);
                        layer.close();
                        if(!rs.error){
                            tree.reload(treeId);
                            loadTree();
                        }
                    },
                    error: function (jqXHR) {
                        layer.close();
                        console.log(jqXHR);
                    }
                });
            }, function(){});

    });

    //新增下一级菜单
    $('.btn-add').on('click',function(){
        if(!_check())
            return;
        let type = $(this).data('type');

        let data = {canUpdate: true,canDelete: true,memberState: true,parentId: selData.treeId,memberType: type};
        load_item(data);
        Function.setForm($('.layui-form'),data,form);
    });

    //移动
    $('.btn-move').on('click',function(){
        if(!_check())
            return;
        let type = $(this).data('type');
        layer.load();

        $.ajax({
            url: "/core/treeinfo/moveMain.do",
            dataType: 'json',
            type: "POST",
            data: {primaryId: selData.treeId,type: type},
            success: function (rs) {
                layer.res(rs);
                layer.close();
                if(!rs.error){
                    loadTree();
                }
            },
            error: function (jqXHR) {
                layer.close();
                console.log(jqXHR);
            }
        });
    });

    //提交表单
    form.on('submit(formDemo)', function(data){
        if(!_check())
            return;
        layer.load();

        $.ajax({
            url: "/core/treeinfo/saveMain.do",
            dataType: 'json',
            type: "POST",
            data: {treeType: treeType,parentId: data.field.parentId, treeId: data.field.memberId,treeName: data.field.memberName},
            success: function (rs) {
                if(!rs.error){
                    let memData = $.extend({},data.field);
                    memData.treeId = rs.msg;
                    $.ajax({
                        url: "/core/memberInfo/saveMain.do",
                        dataType: 'json',
                        type: "POST",
                        data: memData,
                        success: function (rs) {
                            layer.res(rs);
                            layer.close();
                            if(!rs.error){
                                loadTree();
                            }
                        },
                        error: function (jqXHR) {
                            layer.close();
                            layer.res('保存失败！');
                            console.log(jqXHR);
                        }
                    });
                }else{
                    layer.res(rs);
                    layer.close();
                }
            },
            error: function (jqXHR) {
                layer.close();
                layer.res('保存失败！');
                console.log(jqXHR);
            }
        });

        return false;
    });

});